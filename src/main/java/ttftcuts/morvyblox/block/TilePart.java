package ttftcuts.morvyblox.block;

import java.util.ArrayList;
import java.util.List;

import ttftcuts.morvyblox.MorvyBlox;
import ttftcuts.morvyblox.shape.BlockMeta;
import ttftcuts.morvyblox.shape.PartShape;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;

public class TilePart extends TileEntity {
	List<Part> parts;
	
	public TilePart() {
		MorvyBlox.logger.info("tile!");
		
		this.parts = new ArrayList<Part>();
	}
	
	public void addPart(PartShape shape, BlockMeta meta, int x, int y, int z, int orientation) {
		this.parts.add(new Part(shape, meta, x,y,x, orientation));
		this.markTileForUpdate();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		writeCustomNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		readCustomNBT(tag);
	}

	public void writeCustomNBT(NBTTagCompound tag) {
		NBTTagList list = new NBTTagList();
		
		for (Part p : parts) {
			list.appendTag(Part.saveToNBT(p));
		}
		
		tag.setTag("parts", list);
	}

	public void readCustomNBT(NBTTagCompound tag) {
		this.parts.clear();
		NBTTagList list = tag.getTagList("parts", 10);
		
		for (int i=0; i<list.tagCount(); i++) {
			this.parts.add(Part.createFromNBT(list.getCompoundTagAt(i)));
		}
		
		this.markTileForUpdate();
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeCustomNBT(tag);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		readCustomNBT(packet.func_148857_g());
	}
	
	public void markTileForUpdate() {
		this.markDirty();
		if (this.worldObj != null) {
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}
	
	//#######################################################
	public static class Part {
		public final PartShape shape;
		public final BlockMeta blockmeta;
		public int x;
		public int y;
		public int z;
		public int orientation;
		public AxisAlignedBB aabb;
		
		public Part(PartShape shape, BlockMeta blockmeta, int x, int y, int z, int orientation) {
			this.shape = shape;
			this.blockmeta = blockmeta;
			
			this.x = x;
			this.y = y;
			this.z = z;
			this.orientation = orientation;
			
			this.calculateBounds();
		}
		
		public void calculateBounds() {
			this.aabb = AxisAlignedBB.getBoundingBox(0, 0, 0, this.shape.dx, this.shape.dy, this.shape.dz);
		}
		
		public static NBTTagCompound saveToNBT(Part part) {
			NBTTagCompound tag = new NBTTagCompound();
			
			tag.setString("s", part.shape.id);
			tag.setInteger("b", Block.getIdFromBlock(part.blockmeta.block));
			tag.setInteger("m", part.blockmeta.meta);
			tag.setInteger("x", part.x);
			tag.setInteger("y", part.y);
			tag.setInteger("z", part.z);
			tag.setInteger("o", part.orientation);
			
			return tag;
		}
		
		public static Part createFromNBT(NBTTagCompound tag) {
			PartShape shape = PartShape.Shapes.registry.get(tag.getString("s"));
			BlockMeta bm = new BlockMeta(Block.getBlockById(tag.getInteger("b")), tag.getInteger("m"));
			int x = tag.getInteger("x");
			int y = tag.getInteger("y");
			int z = tag.getInteger("z");
			int orientation = tag.getInteger("o");
			
			return new Part(shape,bm,x,y,z,orientation);
		}
		
		public ItemStack getStack() {
			return this.shape.getStack(this.blockmeta);
		}
		
		public boolean render(IBlockAccess world, int x, int y, int z) {
			RenderBlocks r = RenderBlocks.getInstance();
			
			BlockPart.renderMeta = this.blockmeta.meta;
			BlockPart.renderBlock = this.blockmeta.block;
			
			if (this.aabb == null || this.blockmeta == null || this.shape == null) {
				return false; 
			}
			
			r.blockAccess = world;
			
			r.setRenderBounds(this.aabb.minX, this.aabb.minY, this.aabb.minZ, this.aabb.maxX, this.aabb.maxY, this.aabb.maxZ);
			//r.renderStandardBlockWithAmbientOcclusionPartial(this.blockmeta.block, x, y, z, 1.0f, 1.0f, 1.0f);
			r.renderStandardBlock(MorvyBlox.block, x, y, z);

			BlockPart.renderMeta = 0;
			BlockPart.renderBlock = null;
			
			return true;
		}
	}
}
