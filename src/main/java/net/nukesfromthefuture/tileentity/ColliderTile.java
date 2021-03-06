package net.nukesfromthefuture.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.datafix.fixes.TippedArrow;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.nukesfromthefuture.containers.ColliderContainer;
import net.nukesfromthefuture.containers.FluidContainerRegistry;
import net.nukesfromthefuture.containers.FluidTank;
import net.nukesfromthefuture.interfaces.IFluidAcceptor;
import net.nukesfromthefuture.interfaces.IFluidContainer;
import net.nukesfromthefuture.main.FluidHandler;
import net.nukesfromthefuture.main.Nukesfromthefuture;
import net.nukesfromthefuture.saveddata.RadSavedData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ColliderTile extends TileEntity implements IFluidContainer, IFluidAcceptor, ITickableTileEntity, INamedContainerProvider {
    public IItemHandler iInvInstance = iInvReplacement();
    public LazyOptional<IItemHandler> handler = LazyOptional.of(() -> iInvInstance);
    public FluidTank tank;
    public ColliderTile() {
        super(Nukesfromthefuture.collider_tile);
        tank = new FluidTank(FluidHandler.FluidType.black_hole_fuel, 256000, 0);
    }
    //I miss being able to use IInventory :(
    public ItemStackHandler iInvReplacement(){
        return new ItemStackHandler(7){
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if(slot == 0){
                    return stack.getItem() == Nukesfromthefuture.black_hole;
                }else if(slot == 1){
                    return stack.getItem() == Nukesfromthefuture.black_hole;
                }else if(slot == 4){
                    return stack.getItem() == Nukesfromthefuture.black_hole;
                }else if(slot == 3){
                    return stack.getItem() == Nukesfromthefuture.black_hole;
                }else if(slot == 2){
                    return stack.getItem() == Nukesfromthefuture.singularity_magnet;
                }else if(slot == 5){
                    return true;
                }else if(slot == 6){
                    return true;
                }
                return false;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(slot == 0 && stack.getItem() != Nukesfromthefuture.black_hole){
                    return stack;
                }if(slot == 1 && stack.getItem() != Nukesfromthefuture.black_hole){
                    return stack;
                }if(slot == 4 && stack.getItem() != Nukesfromthefuture.black_hole){
                    return stack;
                }if(slot == 3 && stack.getItem() != Nukesfromthefuture.black_hole){
                    return stack;
                }if(slot == 2 && stack.getItem() != Nukesfromthefuture.singularity_magnet){
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("singularity_nuke");
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new ColliderContainer(p_createMenu_1_, this.getPos(), this.getWorld(), p_createMenu_3_, p_createMenu_2_);
    }

    @Override
    public void tick() {
        if(!world.isRemote) {
            tank.loadTank(5, 6, iInvInstance)
            ;
            tank.updateTank(pos.getX(), pos.getY(), pos.getZ(), world.getDimensionKey());


        }
    }
    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        ListNBT list = nbt.getList("items", 10);


        tank.readFromNBT(nbt, "content");

        for(int i = 0; i < list.stream().count(); i++)
        {
            CompoundNBT nbt1 = list.getCompound(i);
            byte b0 = nbt1.getByte("slot");
            if(b0 >= 0 && b0 < iInvInstance.getSlots())
            {
                ItemStack slotContents = iInvInstance.getStackInSlot(b0);
                slotContents = ItemStack.read(nbt1);
            }
        }
    }
    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        ListNBT list = new ListNBT();

        tank.writeToNBT(nbt, "content");

        for(int i = 0; i < iInvInstance.getSlots(); i++)
        {
            if(iInvInstance.getStackInSlot(i) != null)
            {
                CompoundNBT nbt1 = new CompoundNBT();
                nbt1.putByte("slot", (byte)i);
                iInvInstance.getStackInSlot(i).write(nbt1);
                list.add(nbt1);
            }
        }
        nbt.put("items", list);
        return nbt;
    }
    @Override
    public int getMaxFluidFill(FluidHandler.FluidType type) {
        return type.name().equals(tank.getTankType().getName()) ? tank.getMaxFill() : 0;
    }

    @Override
    public void setFillstate(int fill, int index) {
        tank.setFill(fill);
    }

    @Override
    public void setFluidFill(int fill, FluidHandler.FluidType type) {
        if(type.name().equals(tank.getTankType().getName()))
            tank.setFill(fill);
    }

    @Override
    public void setType(FluidHandler.FluidType type, int index) {
        tank.setTankType(type.black_hole_fuel);
    }

    @Override
    public List<FluidTank> getTanks() {
        List<FluidTank> list = new ArrayList();
        list.add(tank);
        return list;
    }

    @Override
    public int getFluidFill(FluidHandler.FluidType type) {
        return type.name().equals(tank.getTankType().getName()) ? tank.getFill() : 0;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return handler.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 6550D;
    }
    public boolean isReady(){
        if(iInvInstance.getStackInSlot(0).getItem() == Nukesfromthefuture.black_hole && iInvInstance.getStackInSlot(1).getItem() == Nukesfromthefuture.black_hole && iInvInstance.getStackInSlot(4).getItem() == Nukesfromthefuture.black_hole && iInvInstance.getStackInSlot(3).getItem() == Nukesfromthefuture.black_hole && iInvInstance.getStackInSlot(2).getItem() == Nukesfromthefuture.singularity_magnet && tank.getFill() >= 128000)
            return true;
        return false;
    }
}
