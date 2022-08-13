package com.nextfeed.library.manager.repository.service;


import com.nextfeed.library.core.entity.MoodEntity;
import com.nextfeed.library.manager.repository.MoodRepository;
import org.springframework.stereotype.Service;

@Service
public class MoodDBService extends AbstractService<MoodEntity, MoodRepository> {

    public MoodDBService(MoodRepository moodRepository) {
        super(moodRepository);
    }
}
