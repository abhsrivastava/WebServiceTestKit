# Replace Spray TestKit with Custom DSL

This DSL provides a drop in replacement of the Spray Test Kit.

This DSL is ideal to be used in projects which have used Spray Test Kit to write unit tests and now want to move away from the Spray Platform.

The current implementation currently uses HTTP4S client. But we can use any library.

The objective is to transparently move away from SprayTestKit without rewriting the test cases.


