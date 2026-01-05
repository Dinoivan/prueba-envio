//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.apache.poi.xssf.usermodel.helpers;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.util.CTColComparator;
import org.apache.poi.xssf.util.NumericRanges;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCol;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCols;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCols.Factory;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorksheet;

import java.util.*;

public class ColumnHelper {
    private CTWorksheet worksheet;

    public ColumnHelper(CTWorksheet worksheet) {
        this.worksheet = worksheet;
        this.cleanColumns();
    }

    public void cleanColumns() {
        TreeSet<CTCol> trackedCols = new TreeSet(CTColComparator.BY_MIN_MAX);
        CTCols newCols = Factory.newInstance();
        CTCols[] colsArray = this.worksheet.getColsArray();

        int i;
        for(i = 0; i < colsArray.length; ++i) {
            CTCols cols = colsArray[i];
            Iterator var6 = cols.getColList().iterator();

            while(var6.hasNext()) {
                CTCol col = (CTCol)var6.next();
                this.addCleanColIntoCols(newCols, col, trackedCols);
            }
        }

        for(int y = i - 1; y >= 0; --y) {
            this.worksheet.removeCols(y);
        }

        newCols.setColArray((CTCol[])trackedCols.toArray(new CTCol[0]));
        this.worksheet.addNewCols();
        this.worksheet.setColsArray(0, newCols);
    }

    public CTCols addCleanColIntoCols(CTCols cols, CTCol col) {
        CTCols newCols = CTCols.Factory.newInstance();
        for (CTCol c : cols.getColArray()) {
            cloneCol(newCols, c);
        }
        cloneCol(newCols, col);
        sortColumns(newCols);
        CTCol[] colArray = newCols.getColArray();
        CTCols returnCols = CTCols.Factory.newInstance();
        sweepCleanColumns(returnCols, colArray, col);
        colArray = returnCols.getColArray();
        cols.setColArray(colArray);
        return returnCols;
    }

    private void addCleanColIntoCols(CTCols cols, CTCol newCol, TreeSet<CTCol> trackedCols) {
        List<CTCol> overlapping = this.getOverlappingCols(newCol, trackedCols);
        if (overlapping.isEmpty()) {
            trackedCols.add(this.cloneCol(cols, newCol));
        } else {
            trackedCols.removeAll(overlapping);
            Iterator var5 = overlapping.iterator();

            while(var5.hasNext()) {
                CTCol existing = (CTCol)var5.next();
                long[] overlap = this.getOverlap(newCol, existing);
                CTCol overlapCol = this.cloneCol(cols, existing, overlap);
                this.setColumnAttributes(newCol, overlapCol);
                trackedCols.add(overlapCol);
                CTCol beforeCol = existing.getMin() < newCol.getMin() ? existing : newCol;
                long[] before = new long[]{Math.min(existing.getMin(), newCol.getMin()), overlap[0] - 1L};
                if (before[0] <= before[1]) {
                    trackedCols.add(this.cloneCol(cols, beforeCol, before));
                }

                CTCol afterCol = existing.getMax() > newCol.getMax() ? existing : newCol;
                long[] after = new long[]{overlap[1] + 1L, Math.max(existing.getMax(), newCol.getMax())};
                if (after[0] <= after[1]) {
                    trackedCols.add(this.cloneCol(cols, afterCol, after));
                }
            }

        }
    }

    private CTCol cloneCol(CTCols cols, CTCol col, long[] newRange) {
        CTCol cloneCol = this.cloneCol(cols, col);
        cloneCol.setMin(newRange[0]);
        cloneCol.setMax(newRange[1]);
        return cloneCol;
    }

    private long[] getOverlap(CTCol col1, CTCol col2) {
        return this.getOverlappingRange(col1, col2);
    }

    private List<CTCol> getOverlappingCols(CTCol newCol, TreeSet<CTCol> trackedCols) {
        CTCol lower = (CTCol)trackedCols.lower(newCol);
        NavigableSet<CTCol> potentiallyOverlapping = lower == null ? trackedCols : trackedCols.tailSet(lower, this.overlaps(lower, newCol));
        List<CTCol> overlapping = new ArrayList();
        Iterator var6 = ((NavigableSet)potentiallyOverlapping).iterator();

        while(var6.hasNext()) {
            CTCol existing = (CTCol)var6.next();
            if (!this.overlaps(newCol, existing)) {
                break;
            }

            overlapping.add(existing);
        }

        return overlapping;
    }

    private boolean overlaps(CTCol col1, CTCol col2) {
        return NumericRanges.getOverlappingType(this.toRange(col1), this.toRange(col2)) != -1;
    }

    private long[] getOverlappingRange(CTCol col1, CTCol col2) {
        return NumericRanges.getOverlappingRange(this.toRange(col1), this.toRange(col2));
    }

    private long[] toRange(CTCol col) {
        return new long[]{col.getMin(), col.getMax()};
    }

    public static void sortColumns(CTCols newCols) {
        CTCol[] colArray = newCols.getColArray();
        Arrays.sort(colArray, CTColComparator.BY_MIN_MAX);
        newCols.setColArray(colArray);
    }

    public CTCol cloneCol(CTCols cols, CTCol col) {
        CTCol newCol = cols.addNewCol();
        newCol.setMin(col.getMin());
        newCol.setMax(col.getMax());
        setColumnAttributes(col, newCol);
        return newCol;
    }

    public CTCol getColumn(long index, boolean splitColumns) {
        return this.getColumn1Based(index + 1L, splitColumns);
    }

    public CTCol getColumn1Based(long index1, boolean splitColumns) {
        CTCols cols = this.worksheet.getColsArray(0);
        CTCol[] colArray = cols.getColArray();
        CTCol[] var6 = colArray;
        int var7 = colArray.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            CTCol col = var6[var8];
            long colMin = col.getMin();
            long colMax = col.getMax();
            if (colMin <= index1 && colMax >= index1) {
                if (splitColumns) {
                    if (colMin < index1) {
                        this.insertCol(cols, colMin, index1 - 1L, new CTCol[]{col});
                    }

                    if (colMax > index1) {
                        this.insertCol(cols, index1 + 1L, colMax, new CTCol[]{col});
                    }

                    col.setMin(index1);
                    col.setMax(index1);
                }

                return col;
            }
        }

        return null;
    }

    private CTCol insertCol(CTCols cols, long min, long max, CTCol[] colsWithAttributes) {
        return this.insertCol(cols, min, max, colsWithAttributes, false, (CTCol)null);
    }

    /*
     * Insert a new CTCol at position 0 into cols, setting min=min, max=max and
     * copying all the colsWithAttributes array cols attributes into newCol
     */
    private CTCol insertCol(CTCols cols, long min, long max,
                            CTCol[] colsWithAttributes, boolean ignoreExistsCheck, CTCol overrideColumn) {
        if(ignoreExistsCheck || !columnExists(cols,min,max)){
            CTCol newCol = cols.insertNewCol(0);
            newCol.setMin(min);
            newCol.setMax(max);
            for (CTCol col : colsWithAttributes) {
                setColumnAttributes(col, newCol);
            }
            if (overrideColumn != null) setColumnAttributes(overrideColumn, newCol);
            return newCol;
        }
        return null;
    }


    public boolean columnExists(CTCols cols, long index) {
        return this.columnExists1Based(cols, index + 1L);
    }

    private boolean columnExists1Based(CTCols cols, long index1) {
        CTCol[] var4 = cols.getColArray();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            CTCol col = var4[var6];
            if (col.getMin() == index1) {
                return true;
            }
        }

        return false;
    }

    public void setColumnAttributes(CTCol fromCol, CTCol toCol) {
        if(fromCol.isSetBestFit()) toCol.setBestFit(fromCol.getBestFit());
        if(fromCol.isSetCustomWidth()) toCol.setCustomWidth(fromCol.getCustomWidth());
        if(fromCol.isSetHidden()) toCol.setHidden(fromCol.getHidden());
        if(fromCol.isSetStyle()) toCol.setStyle(fromCol.getStyle());
        if(fromCol.isSetWidth()) toCol.setWidth(fromCol.getWidth());
        if(fromCol.isSetCollapsed()) toCol.setCollapsed(fromCol.getCollapsed());
        if(fromCol.isSetPhonetic()) toCol.setPhonetic(fromCol.getPhonetic());
        if(fromCol.isSetOutlineLevel()) toCol.setOutlineLevel(fromCol.getOutlineLevel());
        toCol.setCollapsed(fromCol.isSetCollapsed());
    }

    public void setColBestFit(long index, boolean bestFit) {
        CTCol col = this.getOrCreateColumn1Based(index + 1L, false);
        col.setBestFit(bestFit);
    }

    public void setCustomWidth(long index, boolean bestFit) {
        CTCol col = this.getOrCreateColumn1Based(index + 1L, true);
        col.setCustomWidth(bestFit);
    }

    public void setColWidth(long index, double width) {
        CTCol col = this.getOrCreateColumn1Based(index + 1L, true);
        col.setWidth(width);
    }

    public void setColHidden(long index, boolean hidden) {
        CTCol col = this.getOrCreateColumn1Based(index + 1L, true);
        col.setHidden(hidden);
    }

    protected CTCol getOrCreateColumn1Based(long index1, boolean splitColumns) {
        CTCol col = this.getColumn1Based(index1, splitColumns);
        if (col == null) {
            col = this.worksheet.getColsArray(0).addNewCol();
            col.setMin(index1);
            col.setMax(index1);
        }

        return col;
    }

    public void setColDefaultStyle(long index, CellStyle style) {
        this.setColDefaultStyle(index, style.getIndex());
    }

    public void setColDefaultStyle(long index, int styleId) {
        CTCol col = this.getOrCreateColumn1Based(index + 1L, true);
        col.setStyle((long)styleId);
    }

    public int getColDefaultStyle(long index) {
        return this.getColumn(index, false) != null ? (int)this.getColumn(index, false).getStyle() : -1;
    }

    private boolean columnExists(CTCols cols, long min, long max) {
        for (CTCol col : cols.getColArray()) {
            if (col.getMin() == min && col.getMax() == max) {
                return true;
            }
        }
        return false;
    }

    public int getIndexOfColumn(CTCols cols, CTCol searchCol) {
        if (cols != null && searchCol != null) {
            int i = 0;

            for(Iterator var4 = cols.getColList().iterator(); var4.hasNext(); ++i) {
                CTCol col = (CTCol)var4.next();
                if (col.getMin() == searchCol.getMin() && col.getMax() == searchCol.getMax()) {
                    return i;
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    /**
     * @see <a href="http://en.wikipedia.org/wiki/Sweep_line_algorithm">Sweep line algorithm</a>
     */
    private void sweepCleanColumns(CTCols cols, CTCol[] flattenedColsArray, CTCol overrideColumn) {
        List<CTCol> flattenedCols = new ArrayList<CTCol>(Arrays.asList(flattenedColsArray));
        TreeSet<CTCol> currentElements = new TreeSet<CTCol>(CTColComparator.BY_MAX);
        ListIterator<CTCol> flIter = flattenedCols.listIterator();
        CTCol haveOverrideColumn = null;
        long lastMaxIndex = 0;
        long currentMax = 0;
        while (flIter.hasNext()) {
            CTCol col = flIter.next();
            long currentIndex = col.getMin();
            long colMax = col.getMax();
            long nextIndex = (colMax > currentMax) ? colMax : currentMax;
            if (flIter.hasNext()) {
                nextIndex = flIter.next().getMin();
                flIter.previous();
            }
            Iterator<CTCol> iter = currentElements.iterator();
            while (iter.hasNext()) {
                CTCol elem = iter.next();
                if (currentIndex <= elem.getMax()) break; // all passed elements have been purged
                iter.remove();
            }
            if (!currentElements.isEmpty() && lastMaxIndex < currentIndex) {
                // we need to process previous elements first
                insertCol(cols, lastMaxIndex, currentIndex - 1, currentElements.toArray(new CTCol[currentElements.size()]), true, haveOverrideColumn);
            }
            currentElements.add(col);
            if (colMax > currentMax) currentMax = colMax;
            if (col.equals(overrideColumn)) haveOverrideColumn = overrideColumn;
            while (currentIndex <= nextIndex && !currentElements.isEmpty()) {
                Set<CTCol> currentIndexElements = new HashSet<CTCol>();
                long currentElemIndex;

                {
                    // narrow scope of currentElem
                    CTCol currentElem = currentElements.first();
                    currentElemIndex = currentElem.getMax();
                    currentIndexElements.add(currentElem);

                    while (true) {
                        CTCol higherElem = currentElements.higher(currentElem);
                        if (higherElem == null || higherElem.getMax() != currentElemIndex)
                            break;
                        currentElem = higherElem;
                        currentIndexElements.add(currentElem);
                        if (colMax > currentMax) currentMax = colMax;
                        if (col.equals(overrideColumn)) haveOverrideColumn = overrideColumn;
                    }
                }


                if (currentElemIndex < nextIndex || !flIter.hasNext()) {
                    insertCol(cols, currentIndex, currentElemIndex, currentElements.toArray(new CTCol[currentElements.size()]), true, haveOverrideColumn);
                    if (flIter.hasNext()) {
                        if (nextIndex > currentElemIndex) {
                            currentElements.removeAll(currentIndexElements);
                            if (currentIndexElements.contains(overrideColumn)) haveOverrideColumn = null;
                        }
                    } else {
                        currentElements.removeAll(currentIndexElements);
                        if (currentIndexElements.contains(overrideColumn)) haveOverrideColumn = null;
                    }
                    lastMaxIndex = currentIndex = currentElemIndex + 1;
                } else {
                    lastMaxIndex = currentIndex;
                    currentIndex = nextIndex + 1;
                }

            }
        }
        sortColumns(cols);
    }


}
