package script.space.crafting;

import script.*;
import script.base_class.*;
import script.combat_engine.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import script.base_script;

import script.library.space_crafting;
import script.library.space_utils;
import script.library.utils;

public class component_loot extends script.base_script
{
    public component_loot()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "ship_comp.armor_hitpoints_current"))
        {
            space_crafting.initializeSpaceShipComponent(self);
        }
        int flags = getIntObjVar(self, "ship_comp.flags");
        boolean isBitSet = (flags & ship_component_flags.SCF_reverse_engineered) != 0;
        obj_id player = utils.getContainingPlayer(self);
        return SCRIPT_CONTINUE;
    }
    public int OnGetAttributes(obj_id self, obj_id player, String[] names, String[] attribs) throws InterruptedException
    {
        int flags = getIntObjVar(self, "ship_comp.flags");
        boolean isBitSet = (flags & ship_component_flags.SCF_reverse_engineered) != 0;
        if (isBitSet == true)
        {
            int idx = utils.getValidAttributeIndex(names);
            if (idx == -1)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "reverseEngineered";
            attribs[idx] = "";
        }
        else 
        {
            int level = space_crafting.getReverseEngineeringLevel(self);
            int idx = utils.getValidAttributeIndex(names);
            if (idx == -1)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "reverseEngineeringLevel";
            attribs[idx] = Integer.toString(level);
        }
        if (hasObjVar(self, "isMiningLaser"))
        {
            float min = space_crafting.getWeaponMinimumDamage(self);
            float max = space_crafting.getWeaponMaximumDamage(self);
            min = min / 100;
            max = max / 100;
            String atr = min + " - " + max;
            int idx = utils.getValidAttributeIndex(names);
            if (idx == -1)
            {
                return SCRIPT_CONTINUE;
            }
            names[idx] = "miningExtractionRate";
            attribs[idx] = atr;
        }
        return SCRIPT_CONTINUE;
    }
}
