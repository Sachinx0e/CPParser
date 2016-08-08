HEADER_FILE := checkins\CheckinStateUpdater.h

MEMOWN := static Checkin* updateState(Applib::DateTime::LocalDate* date,Rewire::CheckinDataHolder* checkinDataHolder);

IMPORT_HEADER := headers\LocalDateWrapper.h
IMPORT_HEADER := headers\CheckinDataHolderWrapper.h
IMPORT_HEADER := headers\CheckinWrapper.h

IMPORT_SOURCE := StringUtils.h