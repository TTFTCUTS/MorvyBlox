package ttftcuts.morvyblox.item;

import org.lwjgl.opengl.GL11;

import ttftcuts.morvyblox.shape.BlockMeta;
import ttftcuts.morvyblox.shape.PartShape;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemPartRenderer implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		PartShape shape = PartShape.getShapeFromStack(item);
		BlockMeta blockmeta = BlockMeta.getBlockMetaFromStack(item);
		Tessellator tess =  Tessellator.instance;
		
		if (shape == null || blockmeta == null) { return; }
		
		RenderBlocks r = RenderBlocks.getInstance();
		
		boolean trans = blockmeta.block.getRenderBlockPass() == 1;
		
		double ox = shape.dx/2.0;
		double oy = shape.dy/2.0;
		double oz = shape.dz/2.0;
		
		r.setRenderBounds(0, 0, 0, shape.dx, shape.dy, shape.dz);
		
		if (trans) {
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
		}
		
		GL11.glPushMatrix();

		if (type != ItemRenderType.INVENTORY && type != ItemRenderType.EQUIPPED && type != ItemRenderType.EQUIPPED_FIRST_PERSON) {
			float scale = 0.5f;
			GL11.glScalef(scale,scale,scale);
		}
		if (type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
			GL11.glTranslated(0.5, 0.5, 0.5);
		}
		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslated(-ox, -oy, -oz);
		
		// bottom
		tess.startDrawingQuads();
		tess.setNormal(0.0F, -1.0F, 0.0F);
		r.renderFaceYNeg(blockmeta.block, 0.0F, 0.0F, 0.0F, r.getBlockIconFromSideAndMetadata(blockmeta.block, 0, blockmeta.meta));
		//tess.draw();
		
		// top
		//tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		r.renderFaceYPos(blockmeta.block, 0.0F, 0.0F, 0.0F, r.getBlockIconFromSideAndMetadata(blockmeta.block, 1, blockmeta.meta));
		//tess.draw();
		
		// east
		//tess.startDrawingQuads();
		tess.setNormal(0.0F, 0.0F, -1.0F);
		r.renderFaceZNeg(blockmeta.block, 0.0F, 0.0F, 0.0F, r.getBlockIconFromSideAndMetadata(blockmeta.block, 2, blockmeta.meta));
		//tess.draw();
		
		// west
		//tess.startDrawingQuads();
		tess.setNormal(0.0F, 0.0F, 1.0F);
		r.renderFaceZPos(blockmeta.block, 0.0F, 0.0F, 0.0F, r.getBlockIconFromSideAndMetadata(blockmeta.block, 3, blockmeta.meta));
		//tess.draw();
		
		// north
		//tess.startDrawingQuads();
		tess.setNormal(-1.0F, 0.0F, 0.0F);
		r.renderFaceXNeg(blockmeta.block, 0.0F, 0.0F, 0.0F, r.getBlockIconFromSideAndMetadata(blockmeta.block, 4, blockmeta.meta));
		//tess.draw();
		
		// south
		//tess.startDrawingQuads();
		tess.setNormal(1.0F, 0.0F, 0.0F);
		r.renderFaceXPos(blockmeta.block, 0.0F, 0.0F, 0.0F, r.getBlockIconFromSideAndMetadata(blockmeta.block, 5, blockmeta.meta));
		tess.draw();
		
		GL11.glPopMatrix();
		
		if (trans) {
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_BLEND);
		}
	}

}
