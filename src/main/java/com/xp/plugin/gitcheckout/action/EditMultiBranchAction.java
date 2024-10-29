package com.xp.plugin.gitcheckout.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.xp.plugin.gitcheckout.BranchTree;
import com.xp.plugin.gitcheckout.dialog.EditMultiBranchDialog;
import com.xp.plugin.gitcheckout.settings.Branch;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Arrays;
import java.util.List;

public class EditMultiBranchAction extends AbstractTreeAction {
    public EditMultiBranchAction(BranchTree tree) {
        super("Select Common Branch", tree);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        DefaultMutableTreeNode[] selected = selected(e);
        List<Branch.Module> modules = Arrays.stream(selected).map(DefaultMutableTreeNode::getUserObject).map(o -> (Branch.Module) o).toList();
        EditMultiBranchDialog dialog = new EditMultiBranchDialog(e.getProject(),modules);
        dialog.show();
        // if (dialog.isOK()) {
        //     tree.refresh(300);
        // }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        DefaultMutableTreeNode[] selected = selected(e);

        boolean visible = selected.length > 1 && Arrays.stream(selected).allMatch(DefaultMutableTreeNode::isLeaf);
        presentation.setVisible(visible);
    }
}
