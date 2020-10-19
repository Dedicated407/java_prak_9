package ikbo.prak9;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Gui extends JFrame {
    JPanel jPanel = new JPanel();
    JTextField nameStudent = new JTextField(20);
    JTextField pointsStudent = new JTextField(20);
    JTextArea listStudent = new JTextArea(4, 20);
    JLabel jlabel_phio = new JLabel("Фамилия: ");
    JLabel jlabel_points = new JLabel("Кол-во баллов: ");
    JLabel jlabel_list = new JLabel("Список студентов: ");
    JButton button_add = new JButton("Добавить");
    JButton button_search = new JButton("Поиск по Фамилии");
    JButton button_sort = new JButton("Сортировка списка");
    JScrollPane scrollBar = new JScrollPane(listStudent);
    JPanel jPanel1 = new JPanel();

    Gui(String students) {
        super("Список найденных студентов по фамилии");
        setLocation(700, 400);

        JTextArea searchStudents = new JTextArea(4, 20);
        searchStudents.setText(students);
        jPanel1.add(searchStudents);

        getContentPane().add(jPanel1);
        // Устанавливаем оптимальный размер окна
        pack();

        setVisible(true);
    }
    
    Gui(int number_window) {
        super("Список студентов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(1280,1024);
        setLocation(700, 400);

        jPanel.setLayout(new GridLayout(3, 0, 5, 15));
        //jPanel.setSize(1280,1024);
        jPanel.add(jlabel_phio, BorderLayout.EAST);
        jPanel.add(nameStudent);

        button_add.addActionListener(new ButtonAddStudent());
        jPanel.add(button_add);

        jPanel.add(jlabel_points, BorderLayout.WEST);
        jPanel.add(pointsStudent);

        button_search.addActionListener(new ButtonSearchStudent());
        jPanel.add(button_search);

        jPanel.add(jlabel_list);
        readFile();
        jPanel.add(scrollBar, BorderLayout.CENTER);


        button_sort.addActionListener(new ButtonSortStudent());
        jPanel.add(button_sort);

        // Размещаем нашу панель в панели содержим0ого
        getContentPane().add(jPanel);

        // Устанавливаем оптимальный размер окна
        pack();

        setVisible(true);

    }

    class ButtonAddStudent implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            writeStudent(nameStudent.getText(), Integer.parseInt(pointsStudent.getText()));
            readFile();
            nameStudent.setText("");
            pointsStudent.setText("");
        }
    }

    public void writeStudent(String name, int points) {
        try (FileWriter writer = new
                FileWriter("D:\\РТУ МИРЭА\\3 семестр\\Java\\DataBase.txt", true)) {
            // запись всей строки
            writer.write(name + " " + points + "\n");
            writer.flush();//вставили в файл все что было
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    class ButtonSearchStudent implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String searchStudent = "";
            String str_search = nameStudent.getText();
            try (FileReader fileReader = new FileReader("D:\\РТУ МИРЭА\\3 семестр\\Java\\DataBase.txt")) {

                BufferedReader reader = new BufferedReader(fileReader);
                String line = reader.readLine();

                String[] words1, words2;
                words2 = str_search.split(" ");

                while (line != null) {
                    words1 = line.split(" ");

                    if (words1[0].equals(words2[0])) {
                       searchStudent = searchStudent + line + "\n";
                    }

                    line = reader.readLine();
                    words1[0] = null;
                }

                new Gui(searchStudent);

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    class ButtonSortStudent implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            ArrayList<String> words1 = new ArrayList<>();
            words1.clear();
            try (FileReader search = new
                    FileReader("D:\\РТУ МИРЭА\\3 семестр\\Java\\DataBase.txt")) {
                BufferedReader search_buff = new BufferedReader(search);
                String line = search_buff.readLine();

                while (line != null) {
                    words1.add(line);
                    line = search_buff.readLine();
                }

                search.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            try (FileWriter writer = new
                    FileWriter("D:\\РТУ МИРЭА\\3 семестр\\Java\\DataBase.txt", false)) {
                Collections.sort(words1);
                for (int i = 0; i < words1.size(); i++)
                    writer.append(words1.get(i) + "\n");

                writer.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            readFile();
        }
    }

    void readFile() {
        listStudent.setText("");
        try (FileReader fileReader = new
                FileReader("D:\\РТУ МИРЭА\\3 семестр\\Java\\DataBase.txt")) {
            Scanner scan = new Scanner(fileReader);
            while (scan.hasNextLine())
                listStudent.append(scan.nextLine() + "\n");
            fileReader.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
/*
Цель: реализовать приложение где можно хранить студентов,
искать их, добавлять, сортировать
*/
//JOptionPane.showMessageDialog