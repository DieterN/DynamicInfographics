package infographic;

public interface MainPartVisitor {

	public void visit(LeafMainPart part);
	
	public void visit(CompositeMainPart part);
}
