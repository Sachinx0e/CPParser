HEADER_FILE := LocalTime.h

FUNCTION_IGNORE := bool operator!=(LocalTime& rTime);
FUNCTION_IGNORE := LocalTime& operator= (const LocalTime& rTime);

RENAME_FUNC : operator== : equals
RENAME_FUNC : operator> : isAfter
RENAME_FUNC : operator< : isBefore

IMPORT_HEADER := headers\DurationTimeWrapper.h

IMPORT_SOURCE := StringUtils.h

