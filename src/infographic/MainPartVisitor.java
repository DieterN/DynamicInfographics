package infographic;

/**
 * Interface that should be implemented by all who wants to use the visitor pattern
 * with the MainParts.
 * 
 * @author Dieter
 *
 */
public interface MainPartVisitor {

	/**
	 * Call this method to visit a LeafMainPart
	 * 
	 * @param part: part to visit
	 */
	public void visit(LeafMainPart part);

	/**
	 * Call this method to visit a CompositeMainPart.
	 * 
	 * @param part: part to visit
	 */
	public void visit(CompositeMainPart part);
}
