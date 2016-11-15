HEADER_FILE := categories\CategoryManager.h
PARENT_FILE := ItemManager.h := true

FUNCTION_IGNORE :=	Category* getFromQuery(SQLite::Statement& query);

MEMOWN := static CategoryManager* getInstance();

IMPORT_HEADER := headers\CategoryWrapper.h
IMPORT_HEADER := headers\ContentValuesWrapper.h
IMPORT_HEADER := headers\CategoryDataHolderWrapper.h
IMPORT_HEADER := headers\CategoryFilterWrapper.h

IMPORT_SOURCE := StringUtils.h