Cron Expression Parser

This is a basic java based cron expression parser.
Input is a cron expression passed in command line argument and we follow the standard cron notation
for this.

The cron expression should contain 6 fields:
1. Minutes
2. Hours
3. Day of Month
4. Month
5. Day of Week
6. Command

A sample cron expression to be passed:
*/15 0 1,15 * 1-5 /usr/bin

Output:
minute         0 15 30 45
hour           0
day of month   1 15
month          1 2 3 4 5 6 7 8 9 10 11 12
day of week    1 2 3 4 5
command        /usr/bin

This application first parses the cron expression by splitting with whitespaces
and for each field it processes individually depending on the type.

During the processing of each field, we first split the field with commas.
Because a single field can contain multiple values separated by commas.

Then for each field after splitting, we process based on what the field is.
It can be a step value, a range, a single value or a wildcard(*).

Step Value:
A step value is a value that is preceded by a slash (/). It indicates that the field should be incremented by the step value.
For example, in the minutes field, if we have */15, it means that the field should be incremented by 15 minutes.
So the output will be 0, 15, 30, 45.

Inside each field again, we can have a range, a single value or a wildcard(*).

Range:
A range is a value that is preceded by a hyphen (-). It indicates that the field should be incremented by 1.
For example, in any field, if we have 0-5, it means that the field should be incremented by 1.
So the output will be 0, 1, 2, 3, 4, 5.

Single Value:
This is just a plain number that does not have any other special characters. It indicates that the field should be set to that value.
For example, in any field, if we have 5, it means that the field should be set to 5.

Wildcard:
A wildcard is a value that is preceded by an asterisk (*). It indicates that the field should be set to all possible values.

Usage of TreeSet:

We might have a case where a single field separate by commas and each field can have overlapping or duplicate values.
Also, while displaying we need the data to be shown in a sorted way.
So, to achieve this, I have used a treeSet for this.

Example:
1,13,7,9,5-10

This should be displayed as:
1 5 6 7 8 9 10 13