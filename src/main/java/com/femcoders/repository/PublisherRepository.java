package com.femcoders.repository;

import com.femcoders.model.Publisher;

public interface PublisherRepository {
    //CREATE
    Publisher createPublisher(Publisher publisher);

    //READ
    Publisher readPublisherByName(String name);
    Publisher validateExistingPublisher(String name);

    Publisher findById(int id);

}
