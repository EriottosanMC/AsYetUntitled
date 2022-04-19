package asyetuntitled.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import asyetuntitled.client.test.ClientSanityData;
import net.minecraft.client.CameraType;
import net.minecraft.client.Options;

@Mixin(Options.class)
public class MixinOptions {

	@Shadow
	private CameraType cameraType = CameraType.FIRST_PERSON;
	
	@Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/Options;setCameraType(Lnet/minecraft/client/CameraType;)V")
	private void setCameraType(CameraType camera, CallbackInfo cb) 
	{
		ClientSanityData.forceUpdate();
	}
}
