HEADER_FILE := habits\HabitManager.h
PARENT_FILE := ItemManager.h := true

FUNCTION_IGNORE :=	Habit* getFromQuery(SQLite::Statement& query);

MEMOWN := static HabitManager* getInstance();


IMPORT_HEADER := headers\HabitWrapper.h
IMPORT_HEADER := headers\LocalTimeWrapper.h
IMPORT_HEADER := headers\ContentValuesWrapper.h
IMPORT_HEADER := headers\StreaksDataHolderWrapper.h
IMPORT_HEADER := headers\HabitDataHolderTreeWrapper.h
IMPORT_HEADER := headers\HabitDataHolderWrapper.h

IMPORT_SOURCE := IntUtils.h
IMPORT_SOURCE := StringUtils.h