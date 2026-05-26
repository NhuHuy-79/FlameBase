# API Guidelines

## Naming

Prefer concise names:
signIn()

Avoid Firebase raw naming:
signInWithEmailAndPassword()

## Async APIs

- One-shot -> suspend
- Streams -> Flow

## Error Handling

Always return FlameResult
Never expose raw Firebase callbacks