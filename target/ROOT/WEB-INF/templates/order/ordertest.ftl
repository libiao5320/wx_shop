<script type="text/javascript" src="../../js/jquery.min.js"></script>
 <script>
  $(function(){
	 	 $(".tijiao").click(function(){
		 $.ajax({
			 url:"/order/createdOrder.ajax",
			 data:$(this).closest("form").serialize(),
			 dataType:"json",
			 success:function(json){
               alert(json.message)
			 }
		 })
	  }) 
   })
 </script>
<form>
卖家店铺id<input type="text" name="map[storeId]"><br/>
卖家店铺名称<input type="text" name="map[storeName]"><br/>
产品id<input type="text" name="map[goodsId]"><br/>
产品名称<input type="text" name="map[goodsName]"><br/>
产品数量<input type="text" name="map[goodsNum]"><br/>
订单价格<input type="text" name="map[orderAmount]"><br/>
订单类型<input type="text" name="map[orderType]"><br/>
红包ID<input type="text" name="map[rcbId]"><br/>
产品图片<input type="text" name="map[goodsLitpic]"><br/>
红包支付金额<input type="text" name="map[rcbAmount]"><br/>
预支付金额<input type="text" name="map[bookDownPayment]"><br/>
<input type="button" value="立即付款" class="tijiao">
</form>

<form>
 
</form>

