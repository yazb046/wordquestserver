package com.wordquest.server.vocabulary.Service;

import com.wordquest.server.vocabulary.model.Dictionary;
import com.wordquest.server.vocabulary.model.DictionaryRepository;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
public class DictionaryService {
    private DictionaryRepository dictionaryRepository;

    public Dictionary getDictionary(String dictName, Pageable pageable) {
//        Long id = dictionaryRepository.findByDescription(dictName);
//        return dictionaryRepository.find(id, pageable);
        return null;
    }
}
