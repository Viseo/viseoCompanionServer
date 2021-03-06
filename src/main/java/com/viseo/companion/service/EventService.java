package com.viseo.companion.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.viseo.companion.dao.EventDAO;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventDAO eventDAO;
    @Autowired
    private UserService userService;

    @Autowired
    private AmazonS3 s3client;

    @Value("${amazon.s3.bucket}")
    private String nameCardBucket;

    public Event addEvent(Event event) {
        try {
            event.addParticipant(event.getHost());
            event = eventDAO.addEvent(event);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        /*Notification notif = new Notification(event, "/topics/newEvent/");
        notif.sendNotification();*/
        return event;
    }

    public List<Event> getEvents(String before, String after) {
        List<Event> events;
        try {
            if (before != null && after != null) {
                events = eventDAO.getEventsBetween(before, after);
            } else if (before == null && after != null) {
                events = eventDAO.getEventsAfter(after);
            } else if (before != null) {
                events = eventDAO.getEventsBefore(before);
            } else {
                events = eventDAO.getEvents();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    public List<Event> getEventsByRegisteredUser(long userId) {
        try {
            return eventDAO.getEventsByRegisteredUser(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Event getEvent(long id) {
        try {
            return eventDAO.getEvent(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Event updateEvent(Event event) {
        try {
            return eventDAO.updateEvent(event);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void deleteEvent(Long eventId) {
        try {
            Event event = eventDAO.getEvent(eventId);
            if (event != null) {
                eventDAO.deleteEvent(event);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Event addParticipant(long eventId, long userId) {
        try {
            return eventDAO.addParticipant(eventId, userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User getParticipant(long eventId, long userId) {
        Event event = getEvent(eventId);
        if (event != null) {
            for (User user : event.getParticipants()) {
                if (user.getId() == userId) {
                    return user;
                }
            }
        }
        return null;
    }

    public List<User> getParticipants(long eventId) {
        List<User> participants = new ArrayList<>();
        Event event = getEvent(eventId);
        if (event != null) {
            participants.addAll(event.getParticipants());
        }
        return participants;
    }

    public Event removeParticipant(long eventId, long userId) {
        try {
            return eventDAO.removeParticipant(eventId, userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String uploadImage(String filename, MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        PutObjectRequest putObjectRequest = new PutObjectRequest(nameCardBucket, filename, inputStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult result = s3client.putObject(putObjectRequest);
        S3Object s3Object = s3client.getObject(new GetObjectRequest(nameCardBucket, filename));
        return s3Object.getObjectContent().getHttpRequest().getURI().toString();
    }
}
