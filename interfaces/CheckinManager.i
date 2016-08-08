HEADER_FILE := checkins\CheckinManager.h
PARENT_FILE := ItemManager.h := true

FUNCTION_IGNORE :=	Checkin* getFromQuery(SQLite::Statement& query);

MEMOWN := static CheckinManager* getInstance();

IMPORT_HEADER := headers\CheckinWrapper.h
IMPORT_HEADER := headers\LocalTimeWrapper.h
IMPORT_HEADER := headers\ContentValuesWrapper.h
IMPORT_HEADER := headers\StreaksDataHolderWrapper.h
IMPORT_HEADER := headers\CheckinDataHolderWrapper.h
IMPORT_HEADER := headers\CheckinFilterWrapper.h

IMPORT_SOURCE := IntUtils.h
IMPORT_SOURCE := StringUtils.h