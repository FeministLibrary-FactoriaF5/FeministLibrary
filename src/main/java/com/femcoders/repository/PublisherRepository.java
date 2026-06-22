package com.femcoders.repository;

import com.femcoders.model.Publisher;

public interface PublisherRepository {
    Publisher createPublisher(Publisher publisher);
    Publisher readPublisherByName(String name);

}
