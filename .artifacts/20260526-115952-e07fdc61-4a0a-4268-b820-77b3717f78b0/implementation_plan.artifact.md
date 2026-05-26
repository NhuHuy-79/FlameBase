# Flamebase Project Alignment & Refinement Plan

The goal is to ensure the current `flamebase-auth` implementation strictly adheres to the established project rules and architecture before moving to Firestore.

## Proposed Changes

### [Flamebase Auth Refinement]

#### [FlameAuth.kt](file:///C:/Users/badan/MyWorld/Flamebase/flamebase/auth/src/main/java/com/nhuhuy/flamebase/auth/FlameAuth.kt)
- Ensure `authenticatedFlow` is robust and follows the "Streams -> Flow" guideline.
- Verify it remains a "Thin wrapper".

#### [Auth+Login.kt](file:///C:/Users/badan/MyWorld/Flamebase/flamebase/auth/src/main/java/com/nhuhuy/flamebase/auth/operations/Auth+Login.kt) & others
- Verify names follow "Prefer concise names" (e.g., `signIn` instead of `signInWithEmail`).
- Ensure no CancellationException is swallowed in the `FlameCall.call` block.

#### [FlamebaseError.kt](file:///C:/Users/badan/MyWorld/Flamebase/flamebase/core/src/main/java/com/nhuhuy/flamebase/core/result/FlamebaseError.kt)
- Refine the error interface to better support the "sealed interfaces for result/error" rule if needed.

## Verification Plan

### Automated Tests
- Create basic unit tests for the refined wrappers using mockito or fakes.
- Verify that `CancellationException` is propagated correctly.

### Manual Verification
- Review the code structure against `ARCHITECTURE.md` visually.
