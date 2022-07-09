package atomic.mcbomber.modules;

import atomic.mcbomber.Addon;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.events.world.ChunkDataEvent;
import meteordevelopment.meteorclient.systems.modules.world.StashFinder.Chunk;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.entity.*;
import net.minecraft.util.math.ChunkPos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BaseEsp extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<BlockEntityType<?>>> storageBlocks = sgGeneral.add(new StorageBlockListSetting.Builder()
        .name("search-blocks")
        .description("Select the blocks to search for")
        .defaultValue(StorageBlockListSetting.STORAGE_BLOCKS)
        .build()
    );

    private final Setting<Integer> minimumStorageCount = sgGeneral.add(new IntSetting.Builder()
        .name("minimum-storage-cont")
        .description("The minimum amount of blocks in a chunk to record the chunk")
        .defaultValue(4)
        .min(1)
        .sliderMin(1)
        .build()
    );

    public List<Chunk> chunks = new ArrayList<>();

    public BaseEsp() {
        super(Addon.CATEGORY, "base-esp", "Searches loaded chunks for bases");
    }

    @Override
    public void onActivate() {
        load();
    }

    @EventHandler
    private void onChunkData(ChunkDataEvent event) {
        // Check the distance.
        double chunkXAbs = Math.abs(event.chunk.getPos().x * 16);
        double chunkZAbs = Math.abs(event.chunk.getPos().z * 16);
        if (Math.sqrt(chunkXAbs * chunkXAbs + chunkZAbs * chunkZAbs) < 15) return;

        Chunk chunk = new Chunk(event.chunk.getPos());

        for (BlockEntity blockEntity : event.chunk.getBlockEntities().values()) {
            if (!storageBlocks.get().contains(blockEntity.getType())) continue;

            if (blockEntity instanceof ChestBlockEntity) chunk.chests++;
            else if (blockEntity instanceof BarrelBlockEntity) chunk.barrels++;
            else if (blockEntity instanceof ShulkerBoxBlockEntity) chunk.shulkers++;
            else if (blockEntity instanceof EnderChestBlockEntity) chunk.enderChests++;
            else if (blockEntity instanceof AbstractFurnaceBlockEntity) chunk.furnaces++;
            else if (blockEntity instanceof DispenserBlockEntity) chunk.dispensersDroppers++;
            else if (blockEntity instanceof HopperBlockEntity) chunk.hoppers++;
        }

        if (chunk.getTotal() >= minimumStorageCount.get()) {
            Chunk prevChunk = null;
            int i = chunks.indexOf(chunk);

            if (i < 0) chunks.add(chunk);
            else prevChunk = chunks.set(i, chunk);

            saveCsv();

            if (!chunk.equals(prevChunk) || !chunk.countsEqual(prevChunk)) {
                info("Found base at (highlight)%s(default), (highlight)%s(default).", chunk.x, chunk.z);
            }
        }

        ChunkDataEvent.returnChunkDataEvent(event);
    }

    // @Override
    // public WWidget getWidget(GuiTheme theme) {
    //     // Sort
    //     chunks.sort(Comparator.comparingInt(value -> -value.getTotal()));

    //     WVerticalList list = theme.verticalList();

    //     // Clear
    //     WButton clear = list.add(theme.button("Clear")).widget();

    //     WTable table = new WTable();
    //     if (chunks.size() > 0) list.add(table);

    //     clear.action = () -> {
    //         chunks.clear();
    //         table.clear();
    //     };

    //     // Chunks
    //     fillTable(theme, table);

    //     return list;
    // }

    // private void fillTable(GuiTheme theme, WTable table) {
    //     for (Chunk chunk : chunks) {
    //         table.add(theme.label("Pos: " + chunk.x + ", " + chunk.z));
    //         table.add(theme.label("Total: " + chunk.getTotal()));

    //         WButton open = table.add(theme.button("Open")).widget();
    //         open.action = () -> mc.setScreen(new ChunkScreen(theme, chunk));

    //         WButton gotoBtn = table.add(theme.button("Goto")).widget();
    //         gotoBtn.action = () -> BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalXZ(chunk.x, chunk.z));

    //         WMinus delete = table.add(theme.minus()).widget();
    //         delete.action = () -> {
    //             if (chunks.remove(chunk)) {
    //                 table.clear();
    //                 fillTable(theme, table);

    //                 saveCsv();
    //             }
    //         };

    //         table.row();
    //     }
    // }

    private void load() {
        boolean loaded = false;

        File file = getCsvFile();
        if (!loaded && file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                reader.readLine();

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(" ");
                    Chunk chunk = new Chunk(new ChunkPos(Integer.parseInt(values[0]), Integer.parseInt(values[1])));

                    chunk.chests = Integer.parseInt(values[2]);
                    chunk.shulkers = Integer.parseInt(values[3]);
                    chunk.enderChests = Integer.parseInt(values[4]);
                    chunk.furnaces = Integer.parseInt(values[5]);
                    chunk.dispensersDroppers = Integer.parseInt(values[6]);
                    chunk.hoppers = Integer.parseInt(values[7]);

                    chunks.add(chunk);
                }

                reader.close();
            } catch (Exception ignored) {
                if (chunks == null) chunks = new ArrayList<>();
            }
        }
    }

    private void saveCsv() {
        try {
            File file = getCsvFile();
            file.getParentFile().mkdirs();
            Writer writer = new FileWriter(file);

            writer.write("X,Z,Chests,Barrels,Shulkers,EnderChests,Furnaces,DispensersDroppers,Hoppers\n");
            for (Chunk chunk : chunks) chunk.write(writer);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getCsvFile() {
        return new File(new File(new File(MeteorClient.FOLDER, "McBomber"), Utils.getWorldName()), ".bases");
    }

}