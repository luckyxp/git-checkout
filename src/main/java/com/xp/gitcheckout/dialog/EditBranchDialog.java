package com.xp.gitcheckout.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.xp.gitcheckout.settings.Branch;
import git4idea.GitRemoteBranch;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class EditBranchDialog extends DialogWrapper {
    private ComboBox<String> comboBox;
    private final GitRepositoryManager git;

    private final Branch.Module module;

    public EditBranchDialog(Project project, Branch.Module module) {
        super(project, true);
        git = GitRepositoryManager.getInstance(project);
        this.module = module;
        setTitle("Select Branch");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel contentPane = new JPanel(new BorderLayout());
        comboBox = new ComboBox<>();
        for (GitRepository repository : git.getRepositories()) {
            if (repository.getRoot().getName().equals(module.name)) {
                int index = 0;
                for (String branch : repository.getBranches().getRemoteBranches().stream().map(GitRemoteBranch::getName).collect(Collectors.toSet())) {
                    comboBox.addItem(branch);
                    if (module.branch.equals(branch.substring(7))) {
                        comboBox.setSelectedIndex(index);
                    }
                    index++;
                }
                break;
            }
        }

        contentPane.add(new JLabel("branch:"), BorderLayout.NORTH);
        contentPane.add(comboBox, BorderLayout.CENTER);
        return contentPane;
    }

    @Override
    protected void doOKAction() {
        String text = (String) comboBox.getSelectedItem();
        if (text == null || text.isBlank()) {
            JOptionPane.showMessageDialog(null, "Branch cannot be empty");
        }else {
            module.branch = text.substring(7);
            System.out.println(module.name + " changed " + module.branch);
            super.doOKAction();
        }
    }
}