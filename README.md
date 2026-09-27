# zetra-android-demo

A tiny app that shows both ways to add Zetra to an Android app:

1. **Full-screen** — `ZetraChatActivity.start(context, agentId)`
2. **Embedded** — the `ZetraChat` composable inside your own screen

## Run it

```bash
# 1. start the mock API (from the zetra-api repo)
node ../zetra-api/mock-server/server.mjs

# 2. open this folder in Android Studio and run the `app` config on an emulator
```

The app defaults to the **Local mock** (`http://10.0.2.2:4010/v1`, the emulator's alias for your
computer) and the public `agt_demo` agent. Switch to **Production** and enter your own public agent id
to use the real API.

If [`zetra-android`](../zetra-android) is checked out next to this folder, Gradle builds the SDK from
source (see `settings.gradle.kts`); otherwise it pulls `ai.zetra:zetra-android` from Maven Central.

## License

MIT
