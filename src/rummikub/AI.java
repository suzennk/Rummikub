package rummikub;

import java.util.ArrayList;

public class AI extends Player {
   private Rack rack;
   private Boolean enrollment = false;// 등록여부 나타냄
   private Board board;

   public AI(Board board, Rack myRack) {
      super(myRack);
      this.board = board;
      this.rack = myRack;
   }

   public void takeTurn() {
      // turn !
      System.out.println("AI took its turn.");
      rack.print();
      makeNumSet();
      makeColSet();
      if (enrollment) {
         registerRemains();
      }
      if (!hasRegistered()) {
         // 변화없음
         rack.drawFourTiles();
      }

   }

   private void makeNumSet() {// 숫자에 대해서
      rack.sortByNumber();// 숫자에 대해서 sort

      ArrayList<Integer> tileArray = new ArrayList<Integer>();
      for (int i = 0; i < Rack.HEIGHT - 2; i++) {
         for (int j = 0; j < Rack.WIDTH - 2; j++) {
            if (rack.getTileAt(i, j) != 104 && rack.getTileAt(i, j) != 105
                  && (rack.getTileAt((i + j) / Rack.WIDTH, (i + j) % Rack.WIDTH))
                        % 26 == (rack.getTileAt((i + j + 2) / Rack.WIDTH, (i + j + 2) % Rack.WIDTH) % 26)) {// 3개가
                                                                                       // 같은가
               if (rack.getTileAt((i + j + 1) / Rack.WIDTH,
                     (i + j + 1) % Rack.WIDTH) != (rack.getTileAt((i + j) / Rack.WIDTH, (i + j) % Rack.WIDTH))
                     && rack.getTileAt((i + j + 2) / Rack.WIDTH, (i + j + 2) % Rack.WIDTH) != (rack
                           .getTileAt((i + j) / Rack.WIDTH, (i + j) % Rack.WIDTH))) {

                  if (i != Rack.HEIGHT - 3 && j != Rack.WIDTH - 3
                        && (rack.getTileAt((i + j) / Rack.WIDTH, (i + j) % Rack.WIDTH))
                              % 26 == (rack.getTileAt((i + j + 3) / Rack.WIDTH, (i + j + 3) % Rack.WIDTH)
                                    % 26)) {// 4개가 같은가
                     if (rack.getTileAt((i + j + 3) / Rack.WIDTH, (i + j + 3) % Rack.WIDTH) != (rack
                           .getTileAt((i + j) / Rack.WIDTH, (i + j) % Rack.WIDTH))) {
                        for (int index = 0; index < 4; index++) {
                           tileArray.add(
                                 rack.getTileAt((i + j + index) / Rack.WIDTH, (i + j + index) % Rack.WIDTH));
                           rack.removeTileAt((i + j + index) / Rack.WIDTH, (i + j + index) % Rack.WIDTH);
                        }
                        /*
                         * tileArray.add(rack.getTileAt((i + j) /
                         * Rack.WIDTH, (i + j) % Rack.WIDTH));
                         * tileArray.add(rack.getTileAt((i + j + 1) /
                         * Rack.WIDTH, (i + j + 1) % Rack.WIDTH));
                         * tileArray.add(rack.getTileAt((i + j + 2) /
                         * Rack.WIDTH, (i + j + 2) % Rack.WIDTH));
                         * tileArray.add(rack.getTileAt((i + j + 3) /
                         * Rack.WIDTH, (i + j + 3) % Rack.WIDTH));
                         * 
                         * for (int index = 0; index < 4; index++) {
                         * rack.removeTileAt((i+j +index) / Rack.WIDTH,
                         * (i+j+index) % Rack.WIDTH); }
                         */
                     } else { // 3개가 같은 경우
                        /*
                         * tileArray.add(rack.getTileAt((i + j) /
                         * Rack.WIDTH, (i + j) % Rack.WIDTH));
                         * tileArray.add(rack.getTileAt((i + j + 1) /
                         * Rack.WIDTH, (i + j + 1) % Rack.WIDTH));
                         * tileArray.add(rack.getTileAt((i + j + 2) /
                         * Rack.WIDTH, (i + j + 2) % Rack.WIDTH));
                         */
                        for (int index = 0; index < 3; index++) {
                           tileArray.add(
                                 rack.getTileAt((i + j + index) / Rack.WIDTH, (i + j + index) % Rack.WIDTH));
                           rack.removeTileAt((i + j + index) / Rack.WIDTH, (i + j + index) % Rack.WIDTH);
                        }
                     }
                     if (isEnrolled(tileArray)) { // board에 tile을 냈으면(등록
                                             // 했으면)
                        // tileArray = new ArrayList<Integer>();
                        rack.saveCurrentRack();
                     } else { // 등록 안했으면
                        rack.reset();
                        // rack.make1Dto2D(tileArray);
                     }
                  }

               }

            }
         }
      }

   }

   private void makeColSet() {
      rack.sortByColor();// 색깔순으로 sort

      ArrayList<Integer> tileArray = new ArrayList<Integer>();
      
      int index = 0;
      for (int i = 0; i < Rack.HEIGHT - 1; i++) {
         {
            for (int j = 0; j < Rack.WIDTH - 1; j++) {
               if (rack.getTileAt(i, j) != 25 || rack.getTileAt(i, j) != 51 || rack.getTileAt(i, j) != 77
                     || rack.getTileAt(i, j) != 103 || rack.getTileAt(i, j) != 104
                     || rack.getTileAt(i, j) != 105) {// 색깔의 끝이 아니면, 조커아니면
                     if((rack.getTileAt((i + j + 1) / Rack.WIDTH,(i + j + 1) % Rack.WIDTH) %2==0)){//id가 짝수이면
                        if (rack.getTileAt((i + j + 1) / Rack.WIDTH,(i + j + 1) % Rack.WIDTH) 
                              == rack.getTileAt((i + j) / Rack.WIDTH, (i + j) % Rack.WIDTH) + 2||
                              rack.getTileAt((i + j + 1) / Rack.WIDTH,(i + j + 1) % Rack.WIDTH) 
                              == rack.getTileAt((i + j) / Rack.WIDTH, (i + j) % Rack.WIDTH) + 3) {   // 같은 색 연속된 숫자
                              
                           tileArray.add(index, rack.getTileAt(i, j));
                              rack.removeTileAt(i, j);
                              index++;
                        }else{//홀수이면
                           if (rack.getTileAt((i + j + 1) / Rack.WIDTH,(i + j + 1) % Rack.WIDTH) 
                              == rack.getTileAt((i + j) / Rack.WIDTH, (i + j) % Rack.WIDTH) + 1||
                              rack.getTileAt((i + j + 1) / Rack.WIDTH,(i + j + 1) % Rack.WIDTH) 
                              == rack.getTileAt((i + j) / Rack.WIDTH, (i + j) % Rack.WIDTH) + 1) {   // 같은 색 연속된 숫자
                              
                              tileArray.add(index, rack.getTileAt(i, j));
                              rack.removeTileAt(i, j);
                              index++;
                        }
                  if (rack.getTileAt((i + j + 1) / Rack.WIDTH,(i + j + 1) % Rack.WIDTH) 
                        == rack.getTileAt((i + j) / Rack.WIDTH, (i + j) % Rack.WIDTH) + 1) {   // 같은 색 연속된 숫자
                     tileArray.add(index, rack.getTileAt(i, j));
                     rack.removeTileAt(i, j);
                     index++;
                  } else {   // 
                     tileArray.add(index, rack.getTileAt(i, j));
                     if (index >= 2) {// 3개 이상이면
                        if (!isEnrolled(tileArray)) {// 등록안했으면
                           //rack.make1Dto2D(tileArray);// 남은 것을 다시 rack에 넣어준다.
                           rack.reset();
                        } else {
                           rack.saveCurrentRack(); // TODO
                           //tileArray = null;
                        }
                           

                     } else
                        rack.make1Dto2D(tileArray);
                  }
                  index = 0;
               }
            }
         }

      }
      }
    }
   }

   private void registerRemains() {
      // 남은것들을 타일과 비교해줌
      ArrayList<TileSet> tiles = board.getSet();
      TileSet tileSet;

      boolean[] numOrColor = new boolean[tiles.size()];// num=t,color=f
      int[] remove = null;
      int index = 0;

      for (int i = 0; i < tiles.size() - 1; i++) {
         tileSet = tiles.get(i);
         if (tileSet.isAllSameNum())
            numOrColor[i] = true;

         else
            numOrColor[i] = false;
      }
      for (int i = 0; i < tiles.size() - 1; i++) {
         for (int j = 0; j < rack.getCurrentSize() - 1; j++) {
            if (numOrColor[i]) {// 컬러면
               if (tiles.get(i).getTileSet().get(0) % 26 != 0 || tiles.get(i).getTileSet().get(0) % 26 != 1) {// 맨앞수가
                  // 1이
                  // 아니고
                  if(tiles.get(i).getTileSet().get(0)%2==0){
                     if (tiles.get(i).getTileSet().get(0) - 1 == rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH)||
                           tiles.get(i).getTileSet().get(0) - 2 == rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH)) {// 맨앞수보다
                        // 1작다
                        tiles.get(i).getTileSet().add(0, rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH));// 타일
                                                                                       // 맨앞에
                        // 추가해주고
                        board.repaint();
                        remove[index] = j;// 내 렉에서 제거할 부분을 저장한다.
                        index++;
                  }else{
                     if (tiles.get(i).getTileSet().get(0) - 2 == rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH)||
                           tiles.get(i).getTileSet().get(0) - 3 == rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH)) {// 맨앞수보다
                        // 1작다
                        tiles.get(i).getTileSet().add(0, rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH));// 타일
                                                                                       // 맨앞에
                        // 추가해주고
                        board.repaint();
                        remove[index] = j;// 내 렉에서 제거할 부분을 저장한다.
                        index++;
                  }
                     
                  
                  }
               }
               int size = tiles.get(i).getTileSet().size();

               if (tiles.get(i).getTileSet().get(size - 1) % 26 != 24
                     || tiles.get(i).getTileSet().get(size - 1) % 26 != 25) {// 마지막이
                                                               // 13이
                                                               // 아니고
                  if(tiles.get(i).getTileSet().get(size - 1)%2==0){//뒷 수가 짝수면
                     if (tiles.get(i).getTileSet().get(size - 1) +2 == rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH)||
                           tiles.get(i).getTileSet().get(size - 1) +3== rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH)) {// 맨앞수보다
                        // 1작다
                        tiles.get(i).getTileSet().add(rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH));// 타일
                                                                                       // 맨앞에
                        // 추가해주고
                        board.repaint();
                        remove[index] = j;// 내 렉에서 제거할 것숫자를 저장해둔다.
                        index++;
                  }else{
                     if (tiles.get(i).getTileSet().get(size-1) +1 == rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH)||
                           tiles.get(i).getTileSet().get(size-1) +2 == rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH)) {// 맨앞수보다
                        // 1작다
                        tiles.get(i).getTileSet().add(0, rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH));// 타일
                                                                                       // 맨앞에
                        // 추가해주고
                        board.repaint();
                        remove[index] = j;// 내 렉에서 제거할 부분을 저장한다.
                        index++;
                  }
                     
                  
                  }
               }
                  

            } else {// 숫자면
               if (tiles.get(i).getTileSet().size() == 3) {
                  if (tiles.get(i).getTileSet().get(0) % 26 == rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH)
                        % 26) {// 숫자가 같다

                     if (tiles.get(i).getTileSet().get(0) != rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH)
                           && tiles.get(i).getTileSet().get(1) != rack.getTileAt(j / Rack.WIDTH,
                                 j % Rack.WIDTH)
                           && tiles.get(i).getTileSet().get(2) != rack.getTileAt(j / Rack.WIDTH,
                                 j % Rack.WIDTH)) {// 색이 모두 다르면

                        tiles.get(i).getTileSet().add(rack.getTileAt(j / Rack.WIDTH, j % Rack.WIDTH));// 타일에
                        // 추가해주고
                        board.repaint();
                        remove[index] = j;// 내 렉에서 제거할 것숫자를 저장해둔다.
                        index++;
                     }

                  }
               }
            }
         }
      }
   }
      }
      for (int i = remove.length - 1; i > -1; i++) {// 내 렉에서 제거해준다.
         rack.remove(remove[i]);
      }
         
   }

   // 
   private boolean isEnrolled(ArrayList<Integer> tempArr) {
      // 등록했는지 안했는지 알려주는 함수
      int sum = 0;
      if (enrollment) {// 등록했다
         board.addTileSet(new TileSet(tempArr));
         board.repaint();
         return true;// 타일에 넣어줬다.
      } else {// 등록안했으면
         for (int i = 0; i < tempArr.size() - 1; i++) {
            sum += tempArr.get(i);
         }
         if (sum >= 30) {
            board.addTileSet(new TileSet(tempArr));
            board.repaint();
            enrollment = true;
            return true;
         } else
            return false;
      }

   }
}