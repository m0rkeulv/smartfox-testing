Modified version of smecsias smartfox-testing lib.

The only difference (so far) is support for Anotations to tell each test and/or test class what handler class its going to test.

    @Test
    @HandlerToTest(value = MyHandler.class)
    public void testPrivateMessageSendError() {
        User fromUser = player("Guest1");
        User toUser = player("Guest2");
        setPlayersInRoom(fromUser, toUser);

        verifyWhen(
                fromUser,

                doRequest().
                        putString("message", "Hello!").
                        putInt("toUserId", toUser.getId()),
                then(
                        user(toUser).
                                getResponse("privateMessage").
                                putString("message", "Hello!").
                                putInt("fromUserId", fromUser.getId()).
                                withNoUDP()
                )
        );
    }

