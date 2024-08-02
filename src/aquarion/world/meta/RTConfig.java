package aquarion.world.meta;

import aquarion.world.graphs.RTGraph;
import aquarion.world.interfaces.HasRT;
import aquarion.world.meta.AquaStat;
import arc.*;
import arc.graphics.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class RTConfig {
    public boolean connects;
    public float rotationPower = 0f;
    public boolean graphs = true;
    Color color = Color.valueOf("92dd7e");

    public void addBars(Block block) {
        block.addBar("rotation-power", b -> {
            HasRT build = (HasRT) b;
            return new Bar(
                    () -> Core.bundle.get("rotation-power", "Rotation Power") + ": " + build.rTGraph().getTotalRotationPower(),
                    () -> color.cpy().lerp(Pal.remove, build.rTGraph().getTotalRotationPower() / 100f),
                    () -> build.rTGraph().getTotalRotationPower() / 100f
            );
        });
    }
}