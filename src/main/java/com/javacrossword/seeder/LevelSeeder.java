package com.javacrossword.seeder;

import com.javacrossword.model.Level;
import com.javacrossword.model.Word;
import com.javacrossword.repository.LevelRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LevelSeeder implements CommandLineRunner {

    private final LevelRepository levelRepository;

    public LevelSeeder(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public void run(String... args) {

        boolean seed = false;

        for (String arg : args) {
            if (arg.equals("--seed")) {
                seed = true;
            }
        }

        if (!seed) {
            return;
        }

        if (!levelRepository.existsByName("Chapter One - This is me.")) {

            Level level = new Level();

            level.setName("Chapter One - This is me.");
            level.setAudio("https://res.cloudinary.com/demo/video/upload/daily-routine.mp3");
            level.setPeriod("A1");

            level.setWords(List.of(
                createWord("TWENTY", 0, 0, "horizontal"),
                createWord("WANTED", 0, 1, "vertical"),
                createWord("DOG", 0, 9, "vertical"),
                createWord("DANIEL", 0, 9, "horizontal"),
                createWord("LIKE", 0, 14, "vertical"),
                createWord("HAPPY", 2, 3, "vertical"),
                createWord("HELPING", 2, 3, "horizontal"),
                createWord("NEW", 2, 8, "vertical"),
                createWord("WORK", 2, 11, "horizontal"),
                createWord("PEOPLE", 4, 0, "horizontal"),
                createWord("FREE", 5, 7, "vertical"),
                createWord("QUIET", 5, 10, "vertical"),
                createWord("DAYS", 5, 13, "vertical"),
                createWord("MYSTERIOUS", 6, 2, "horizontal"),
                createWord("STORIES", 6, 4, "vertical"),
                createWord("PERSON", 8, 0, "horizontal"),
                createWord("PLAYING", 8, 0, "vertical"),
                createWord("YEARS", 8, 9, "horizontal"),
                createWord("RECEIVE", 8, 12, "vertical"),
                createWord("SMALL", 9, 8, "vertical"),
                createWord("LIVE", 10, 3, "horizontal"),
                createWord("TIME", 11, 2, "vertical"),
                createWord("CHESS", 11, 10, "horizontal"),
                createWord("SEE", 11, 14, "vertical"),
                createWord("SIMPLE", 12, 4, "horizontal"),
                createWord("MAX", 12, 6, "vertical"),
                createWord("NAME", 13, 0, "horizontal"),
                createWord("LIFE", 14, 9, "horizontal")
            ));

            levelRepository.save(level);
        }
    }

    private Word createWord(
        String word,
        Integer row,
        Integer col,
        String direction
    ) {
        Word newWord = new Word();

        newWord.setWord(word);
        newWord.setRow(row);
        newWord.setCol(col);
        newWord.setDirection(direction);

        return newWord;
    }
}