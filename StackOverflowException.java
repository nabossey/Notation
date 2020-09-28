/**
 *
 * @author nanaa
 */
class StackOverflowException extends Exception {

    public StackOverflowException(String stack_is_full) {
        super(stack_is_full);
    }

}
