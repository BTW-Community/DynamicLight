package net.fabricmc.dynamiclight.mixin;

import btw.block.BTWBlocks;
import btw.community.dynamiclight.DynamicLightAddon;
import btw.world.util.BlockPos;
import net.fabricmc.dynamiclight.BTWLightSource;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public abstract class EntityPlayerMixin {
	private int dynamicLightUpdateTimer;
	public boolean isholdingtorch;

	// 在构造函数中初始化您的自定义成员变量
	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(CallbackInfo info) {
		dynamicLightUpdateTimer = 0;
		isholdingtorch = false;
	}

	@Inject(method = "onUpdate", at = @At("RETURN"))
	private void onUpdate(CallbackInfo info) {
		EntityPlayer player = (EntityPlayer) (Object) this;
		if (!player.worldObj.isRemote)
		{
			dynamicLightUpdateTimer++;
			if (isholdingtorch || dynamicLightUpdateTimer > 9)
			{
				ItemStack heldItem = player.getHeldItem();
				dynamicLightUpdateTimer = 0;
				if (heldItem!= null && isDynamicLightSource(heldItem.itemID))
				{
					isholdingtorch =true;
//        			System.out.println(this + " is holding " + getHeldItem());
				}
				else
				{
					isholdingtorch =false;
				}

				if (isholdingtorch)
				{
					BlockPos lightpos = new BlockPos(MathHelper.floor_double( player.posX ),
							MathHelper.floor_double(player.boundingBox.maxY), MathHelper.floor_double( player.posZ));
//					System.out.println(lightpos.x + "," + lightpos.y + "," + lightpos.z +
//							":" + player.worldObj.getBlockId(lightpos.x, lightpos.y, lightpos.z));
					if (player.worldObj.getBlockId(lightpos.x, lightpos.y, lightpos.z) == 0)
					{
						player.worldObj.setBlock(lightpos.x, lightpos.y, lightpos.z,
								DynamicLightAddon.lightsourceinvis.blockID, 0 , 2);
						player.worldObj.scheduleBlockUpdate(lightpos.x, lightpos.y, lightpos.z,
								DynamicLightAddon.lightsourceinvis.blockID, BTWLightSource.lightSourceTickRate);
					}
				}
			}
		}

	}
	public boolean isDynamicLightSource(int itemID)
	{
//    	Block.blocksList[itemID].lightValue>0 TODO
		return itemID == BTWBlocks.infiniteBurningTorch.blockID || itemID == BTWBlocks.finiteBurningTorch.blockID;
	}

}
