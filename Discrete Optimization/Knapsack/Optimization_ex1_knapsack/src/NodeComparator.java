import java.util.Comparator;


class NodeComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		BetterTreeNode n1 = (BetterTreeNode)o1;
		BetterTreeNode n2 = (BetterTreeNode)o2;

        if ((n1.opt)<(n2.opt))
               return 1;
        if ((n2.opt)<(n1.opt))
               return -1;

        return 0;
	}


//    public int compare(Object o1, Object o2){
//
//    	TreeNode n1 = (TreeNode)o1;
//    	TreeNode n2 = (TreeNode)o2;
//
//        if ((n1.optimalRelaxaition)<(n2.optimalRelaxaition))
//               return 1;
//        if ((n2.optimalRelaxaition)<(n1.optimalRelaxaition))
//               return -1;
//
//        return 0;
//    }


}

