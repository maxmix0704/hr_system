package ua.netcracker.model.dao;

import ua.netcracker.model.entity.InterviewDaysDetails;

/**
 * Created by MaXim on 01.05.2016.
 */
public interface InterviewDaysDetailsDAO extends DAO<InterviewDaysDetails> {
    public boolean update(InterviewDaysDetails interviewDaysDetails);
    public boolean remove(long id);
    public boolean remove(InterviewDaysDetails interviewDaysDetails);
}
