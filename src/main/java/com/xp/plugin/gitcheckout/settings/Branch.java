package com.xp.plugin.gitcheckout.settings;

import java.util.ArrayList;
import java.util.List;

public class Branch{
    public String name;
    public List<Module> modules = new ArrayList<>();

    @Override
    public String toString() {
        return name;
    }
    public static class Module{
        public String name;
        public String branch;

        @Override
        public String toString() {
            return name + ">>>>>>" + branch;
        }
    }
}


