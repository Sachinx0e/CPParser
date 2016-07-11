HEADER_FILE := habits\HabitManager.h


FUNCTION_IGNORE :=	Habit* getFromQuery(SQLite::Statement& query);

MEMOWN := static HabitManager* getInstance();


IMPORT_HEADER := headers\HabitWrapper.h
IMPORT_HEADER := headers\LocalTimeWrapper.h
IMPORT_HEADER := headers\ContentValuesWrapper.h

IMPORT_SOURCE := IntUtils.h
IMPORT_SOURCE := StringUtils.h