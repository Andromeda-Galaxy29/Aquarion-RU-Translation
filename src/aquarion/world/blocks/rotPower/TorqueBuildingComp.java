package aquarion.world.blocks.rotPower;

import aquarion.blocks.TorqueBlocks;
import arc.math.Mathf;
import arc.struct.IntSet;
import arc.util.Nullable;
import mindustry.gen.Building;
import mindustry.world.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mindustry.Vars.tilesize;

public class TorqueBuildingComp extends Building {
    private List<TorqueBlock> connectedTorqueBlocks = new ArrayList<>();
    private float sharedTorque = 0f; // Shared torque value

    public List<TorqueBlock> getConnectedTorqueBlocks() {
        connectedTorqueBlocks.clear();
        collectConnectedTorqueBlocks(this, new IntSet());
        return connectedTorqueBlocks;
    }

    private void collectConnectedTorqueBlocks(Building start, IntSet cameFrom) {
        if (!cameFrom.add(start.id)) return; // Avoid cycles

        if (start instanceof TorqueBlock torqueBlock) {
            connectedTorqueBlocks.add(torqueBlock);
        }

        for (var build : start.proximity) {
            if (build != null && build.team == start.team && build instanceof TorqueBlock torqueE) {
                if (!build.block.rotate || relativeTo(build) == build.rotation) {
                    collectConnectedTorqueBlocks((Building) torqueE, cameFrom);
                }
            }
        }
    }

    public void setSharedTorque(float torque) {
        this.sharedTorque = torque;
        // Propagate torque to all connected blocks
        for (TorqueBlock block : getConnectedTorqueBlocks()) {
            if (block instanceof TorqueShaft.TorqueShaftBuild build) {
                build.setTorque(torque);
            }
        }
    }

    public float getSharedTorque() {
        return sharedTorque;
    }
}