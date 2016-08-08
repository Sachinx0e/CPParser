HEADER_FILE := checkins\CheckinDataHolder.h
PARENT_FILE := DataHolder.h := true

MEMOWN := Checkin* getRef(long position);
MEMOWN := Checkin* getForDateRef(const Applib::DateTime::LocalDate& date, int position, int range);
MEMOWN := Checkin* getForDateRef(const Applib::DateTime::LocalDate& date);

CONSTRUCTOR_IGNORE := CheckinDataHolder(std::vector<Checkin*>* checkins, CheckinFilter* filter);

FUNCTION_IGNORE := bool operator==(const Items::Item& item);

IMPORT_HEADER := headers\CheckinWrapper.h

IMPORT_SOURCE := StringUtils.h