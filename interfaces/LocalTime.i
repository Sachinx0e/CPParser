HEADER_FILE : LocalTime.h

FUNCTIONS_IGNORE_START
bool operator!=(LocalTime& rTime);
FUNCTIONS_IGNORE_END

RENAME_FUNC : operator== : equals
RENAME_FUNC : operator> : isAfter
RENAME_FUNC : operator< : isBefore


IMPORTS_START

headers\DurationTimeWrapper.h

IMPORTS_END
