HEADER_FILE := reasons\ReasonManager.h
PARENT_FILE := ItemManager.h := true

FUNCTION_IGNORE :=	Reason* getFromQuery(SQLite::Statement& query);

MEMOWN := static ReasonManager* getInstance();

IMPORT_HEADER := headers\ReasonsWrapper.h
IMPORT_HEADER := headers\LocalTimeWrapper.h
IMPORT_HEADER := headers\ContentValuesWrapper.h
IMPORT_HEADER := headers\ReasonDataHolderWrapper.h
IMPORT_HEADER := headers\ReasonFilterWrapper.h

IMPORT_SOURCE := StringUtils.h