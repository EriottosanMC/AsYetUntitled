package asyetuntitled.client.particle;

import asyetuntitled.common.util.DarknessHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SpookyEyesParticle extends TextureSheetParticle
{

	protected SpookyEyesParticle(ClientLevel level, double xCoord, double yCoord, double zCoord,
			double xSpeed, double ySpeed, double zSpeed) {
		super(level, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed);
		
		this.setSize(0.2F, 0.2F);
		this.scale(this.random.nextFloat() * 5F);
		this.xd *= 0.02F;
		this.yd *= 0.02F;
		this.zd *= 0.02F;
		
		this.setColor(1.0F, 1.0F, 1.0F);
		
		this.lifetime = (int) (20F / level.random.nextFloat());
		
		
	}
	@Override
	protected int getLightColor(float p_107249_) 
	{
		return LightTexture.FULL_BRIGHT;
	}
	
	@Override
	public ParticleRenderType getRenderType()
	{
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		//Remove if the brightness increases or the player get close
		BlockPos pos = new BlockPos(this.x, this.y, this.z);
		if((level.getSkyFlashTime() > 0 && level.canSeeSky(pos)) || !DarknessHelper.isTrueDarkness(level, pos) || !level.getBlockState(pos).isAir() || Minecraft.getInstance().player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) <  30)
		{
			this.remove();
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType>
	{
		private final SpriteSet sprites;

		public Provider(SpriteSet sprite) {
	         this.sprites = sprite;
		}
		
		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x,
				double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			SpookyEyesParticle spookyEyes = new SpookyEyesParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
			spookyEyes.pickSprite(this.sprites);
			return spookyEyes;
		}
		
	}
}
