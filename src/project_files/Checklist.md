# Priority Checklist

--- 
## High
- [x] Fix combo boxes that calculate how many appointments exist
- [x] Make a report tab count number of Appointments, Users, Contacts, Logins
- [x] Validate time overlapping ~~with alert~~
- [x] When updating appointment, don't allow user to choose time earlier than start
- [x] When customer deleted, show custom message alert of deleted customer
- [ ] Fix EST zonedDateTime
- [ ] Validate Customer having overlapping appointments 
- [ ] If appointments are outside of business hours (Including weekends)


## Medium
- [ ] Fix Alert within 15 minutes
- [x] Remove showing password on login_activity.txt

## Low
- [ ] Eliminate Dependencies with DBAppoitnment class and ReportsController
- [ ] Fix red password error
- [ ] Fix clear button on ModifyAppointment to clear comboboxes using .clear() instead of null.
- [ ] Fix pressing enter when button highlighted
- [ ] Refactor ChangeScreen Util
- [ ] Remove UtilityInterfaces
- [ ] Fix UI size for each page
- [x] Fix tabbing issues modify appointment
- [ ] Add Javadocs annotations
- [ ] Create UML diagram to insert into project
- [ ] Delete all unnecessary files
- [ ] Remove JavaFX error version 16 from pages
- [ ] Create Javadocs
- [ ] Can I use javax.validation API?
- [ ] Add information to README
- [ ] Fix README.md to a textfile instead of a .md file



---

# Essential Tests

- [ ] Scheduling after hours (last appt @ 21:45)
- [ ] Adding too much text to description
- [ ] Adding too much text to title