package com.kim8x.gui;

import com.kim8x.nlp.Pipeline;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import javax.swing.border.TitledBorder;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.List;

public class NLPInterface extends JFrame {

    private JTextArea inputArea, outputArea;
    private JButton loadButton, analyzeButton;
    private JFileChooser fileChooser;
    private final Color PRIMARY_COLOR = new Color(66, 133, 244);  // Google Blue
    private final Color ACCENT_COLOR = new Color(52, 168, 83);    // Google Green
    private final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private final Color TEXT_AREA_BG = new Color(255, 255, 255);
    private final Color OUTPUT_BG = new Color(245, 245, 245);

    public NLPInterface() {
        setTitle("Stanford NLP Analyzer");
        setSize(1000, 700);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BACKGROUND_COLOR);

        // === Font Setup ===
        Font titleFont = new Font("Segoe UI", Font.BOLD, 14);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Get a font that can display emojis
        Font buttonFont = getEmojiCompatibleFont(13);

        // === Header Panel ===
        JPanel headerPanel = createHeaderPanel(titleFont);

        // === Input Area ===
        inputArea = new JTextArea();
        inputArea.setFont(textFont);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setBackground(TEXT_AREA_BG);
        inputArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputScroll.setBorder(createTitledBorder("Input Text", titleFont));

        // === Output Area ===
        outputArea = new JTextArea();
        outputArea.setFont(textFont);
        outputArea.setEditable(false);
        outputArea.setBackground(OUTPUT_BG);
        outputArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(createTitledBorder("NLP Analysis Output", titleFont));

        // === Buttons ===
        JPanel buttonPanel = createButtonPanel(buttonFont);

        // === Split Pane ===
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, inputScroll, outputScroll);
        splitPane.setDividerLocation(280);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);

        // === Status Bar ===
        JPanel statusBar = createStatusBar();

        // === Main Container with Padding ===
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(buttonPanel, BorderLayout.WEST);
        contentPanel.add(splitPane, BorderLayout.CENTER);
        contentPanel.add(statusBar, BorderLayout.SOUTH);

        add(contentPanel);

        // === Actions ===
        fileChooser = new JFileChooser();
        loadButton.addActionListener(this::loadFile);
        analyzeButton.addActionListener(this::analyzeText);
    }

    private JPanel createHeaderPanel(Font titleFont) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);

        JLabel titleLabel = new JLabel("Stanford NLP Text Analysis");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(PRIMARY_COLOR);

        JLabel subtitleLabel = new JLabel("Process natural language text with Stanford CoreNLP");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(Color.DARK_GRAY);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.CENTER);
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        return headerPanel;
    }

    private JPanel createButtonPanel(Font buttonFont) {
        // Using hex values for emojis instead of raw characters
        String folderEmoji = new String(Character.toChars(0x1F4C1));  // 📁 folder
        String brainEmoji = new String(Character.toChars(0x1F9E0));   // 🧠 brain

        loadButton = createStyledButton(folderEmoji + " Load Text", buttonFont, new Color(230, 240, 255));
        analyzeButton = createStyledButton(brainEmoji + " Analyze", buttonFont, new Color(230, 255, 230));

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 0, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 0, 15));

        buttonPanel.add(loadButton);
        buttonPanel.add(analyzeButton);
        buttonPanel.add(new JLabel()); // Spacer

        // Add explanation label
        JLabel infoLabel = new JLabel("<html><body style='width: 120px'>" +
                "Enter or load text to analyze syntax, semantics, and named entities</body></html>");
        infoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        infoLabel.setForeground(Color.DARK_GRAY);
        buttonPanel.add(infoLabel);

        return buttonPanel;
    }

    private JButton createStyledButton(String text, Font font, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(new CompoundBorder(
                new LineBorder(bgColor.darker(), 1),
                new EmptyBorder(8, 15, 8, 15)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(240, 240, 240));
        statusBar.setBorder(new EmptyBorder(5, 10, 5, 10));

        JLabel statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusLabel.setForeground(Color.DARK_GRAY);

        statusBar.add(statusLabel, BorderLayout.WEST);
        return statusBar;
    }

    private TitledBorder createTitledBorder(String title, Font font) {
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                title);
        titledBorder.setTitleFont(font);
        titledBorder.setTitleColor(PRIMARY_COLOR);
        titledBorder.setTitlePosition(TitledBorder.ABOVE_TOP);
        return titledBorder;
    }

    private void loadFile(ActionEvent e) {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                inputArea.read(reader, null);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to load file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void analyzeText(ActionEvent e) {
        String text = inputArea.getText();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please load or type some text.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder result = new StringBuilder();
        StanfordCoreNLP pipeline = Pipeline.getPipeline();

        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            result.append("Sentence: ").append(sentence.toString()).append("\n");
            result.append("Sentiment: ").append(sentiment).append("\n");

            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.word();
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                result.append(String.format("  %-12s  POS: %-6s  NER: %-10s  Lemma: %-12s\n", word, pos, ner, lemma));
            }

            result.append("\n---\n\n");
        }

        outputArea.setText(result.toString());
    }

    public static void main(String[] args) {
        try {
            // Set System look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new NLPInterface().setVisible(true));
    }

    /**
     * Get a font that can display emoji characters
     * @param size the desired font size
     * @return a font that supports emoji
     */
    private Font getEmojiCompatibleFont(int size) {
        // Array of font families that commonly support emojis, in order of preference
        String[] fontFamilies = {
                "Segoe UI Emoji",  // Windows
                "Apple Color Emoji", // macOS
                "Noto Color Emoji", // Linux
                "Segoe UI Symbol", // Windows fallback
                "Symbola",         // Unicode fallback
                "Segoe UI"         // Default fallback
        };

        // Try each font
        for (String fontFamily : fontFamilies) {
            Font font = new Font(fontFamily, Font.BOLD, size);
            if (font.canDisplay(0x1F4C1) && font.canDisplay(0x1F9E0)) {
                return font;
            }
        }

        // If no suitable emoji font is found, return default with bold
        return new Font("Segoe UI", Font.BOLD, size);
    }


}
