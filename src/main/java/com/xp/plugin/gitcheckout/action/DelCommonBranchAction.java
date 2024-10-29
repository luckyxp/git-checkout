package com.xp.plugin.gitcheckout.action;

import com.intellij.openapi.actionSystem.*;
import com.xp.plugin.gitcheckout.BranchTree;
import com.xp.plugin.gitcheckout.settings.Branch;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Arrays;

public class DelCommonBranchAction extends AbstractTreeAction {

    public DelCommonBranchAction(BranchTree tree) {
        super("Delete Common Branch", tree);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        DefaultMutableTreeNode[] selected = selected(e);
        if (settings.branches.removeIf(branch -> Arrays.stream(selected).anyMatch(node -> branch.name.equals(((Branch)node.getUserObject()).name)))) {
            tree.refresh(0);
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        DefaultMutableTreeNode[] selected = selected(e);

        boolean visible = selected.length != 0 && Arrays.stream(selected).allMatch(node -> node.getLevel() == 1);
        presentation.setVisible(visible);
    }


}
