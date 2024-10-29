package com.xp.plugin.gitcheckout.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.xp.plugin.gitcheckout.settings.Branch;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class EditMultiBranchDialog extends DialogWrapper {
    private JTextField inputField;
    private final List<Branch.Module> modules;

    public EditMultiBranchDialog(Project project, List<Branch.Module> modules) {
        super(project, true);
        this.modules = modules;
        setTitle("Edit Branch");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel contentPane = new JPanel(new BorderLayout());
        inputField = new JTextField();
        contentPane.add(new JLabel("branch:"), BorderLayout.NORTH);
        contentPane.add(inputField, BorderLayout.CENTER);
        return contentPane;
    }

    @Override
    protected void doOKAction() {
        String text = inputField.getText();
        if (text == null || text.isBlank()) {
            JOptionPane.showMessageDialog(null, "Branch cannot be empty");
        }else {
            for (Branch.Module module : modules) {
                module.branch = text;
            }
            super.doOKAction();
        }
    }
}