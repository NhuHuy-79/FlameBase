package com.nhuhuy.flamebase.core.result

/**
 * Base interface for all errors in the Flamebase ecosystem.
 */
interface FlamebaseError {
    /**
     * Generic error objects for common failures.
     */
    object Unknown : FlamebaseError
    object Network : FlamebaseError
}
