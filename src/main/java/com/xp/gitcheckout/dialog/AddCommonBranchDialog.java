package com.xp.gitcheckout.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.xp.gitcheckout.settings.Branch;
import com.xp.gitcheckout.settings.GitCheckoutSettings;
import com.xp.gitcheckout.settings.GitCheckoutState;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class AddCommonBranchDialog extends DialogWrapper {
    private JTextField inputField;
    private final GitCheckoutState settings ;

    private final GitRepositoryManager git;

    public AddCommonBranchDialog(Project project, boolean canBeParent) {
        super(project, canBeParent);
        git = GitRepositoryManager.getInstance(project);
        settings = GitCheckoutSettings.settings(project);
        setTitle("Add Common Branch");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel contentPane = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setText("new_branch");
        contentPane.add(new JLabel("common branch name:"), BorderLayout.NORTH);
        contentPane.add(inputField, BorderLayout.CENTER);
        return contentPane;
    }

    @Override
    protected void doOKAction() {
        String text = inputField.getText();
        if (text.isBlank()) {
            JOptionPane.showMessageDialog(null, "Branch cannot be empty");
        } else if (settings.branches.stream().anyMatch(b -> b.name.equals(text))) {
            JOptionPane.showMessageDialog(null, "Branch already exists");
        } else {
            Branch branch = new Branch();
            branch.name = text;
            for (GitRepository repository : git.getRepositories()) {
                Branch.Module module = new Branch.Module();
                module.name = repository.getRoot().getName();
                module.branch = repository.getCurrentBranchName();
                branch.modules.add(module);
            }
            if (!branch.modules.isEmpty()) {
                settings.branches.add(branch);
            }
            super.doOKAction();
        }
    }
}
