package cn.ucai.xm_fulicenter.bean;

/**
 * Created by xiaomiao on 2016/10/14.
 */
public class CollectBean {
        /**
         * id : 7672
         * userName : 7672
         * goodsId : 7672
         * goodsName : 趣味煮蛋模具
         * goodsEnglishName : Kotobuki
         * goodsThumb : http://121.197.1.20/images/201507/thumb_img/6372_thumb_G_1437108490316.jpg
         * goodsImg : http://121.197.1.20/images/201507/1437108490034171398.jpg
         * addTime : 1442419200000
         */

        private int id;
        private int userName;
        private int goodsId;
        private String goodsName;
        private String goodsEnglishName;
        private String goodsThumb;
        private String goodsImg;
        private long addTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserName() {
            return userName;
        }

        public void setUserName(int userName) {
            this.userName = userName;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsEnglishName() {
            return goodsEnglishName;
        }

        public void setGoodsEnglishName(String goodsEnglishName) {
            this.goodsEnglishName = goodsEnglishName;
        }

        public String getGoodsThumb() {
            return goodsThumb;
        }

        public void setGoodsThumb(String goodsThumb) {
            this.goodsThumb = goodsThumb;
        }

        public String getGoodsImg() {
            return goodsImg;
        }

        public void setGoodsImg(String goodsImg) {
            this.goodsImg = goodsImg;
        }

        public long getAddTime() {
            return addTime;
        }

        public void setAddTime(long addTime) {
            this.addTime = addTime;
        }

    public CollectBean() {
    }

    @Override
    public String toString() {
        return "CollectBean{" +
                "id=" + id +
                ", userName=" + userName +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsEnglishName='" + goodsEnglishName + '\'' +
                ", goodsThumb='" + goodsThumb + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", addTime=" + addTime +
                '}';
    }
}

