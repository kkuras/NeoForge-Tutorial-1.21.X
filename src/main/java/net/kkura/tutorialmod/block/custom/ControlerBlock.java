package net.kkura.tutorialmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ControlerBlock extends Block {
    public ControlerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.isShiftKeyDown()) { // Verifica se o player está segurando o shift
            if (!level.isClientSide) {
                if (validStructure(level, pos)) {
                    player.sendSystemMessage(Component.literal("Estrutura montada corretamente!"));
                } else {
                    player.sendSystemMessage(Component.literal("Estrutura incorreta."));
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    public boolean validStructure(Level level, BlockPos pos) {
        // Verifica as 6 direções ao redor do bloco de controle
        BlockPos[] directions = {
                pos.north(), // Frente
                pos.south(), // Trás
                pos.east(),  // Direita
                pos.west(),  // Esquerda
                pos.above(), // Acima
                pos.below()  // Abaixo
        };

        // Verifica se algum dos blocos adjacentes é de ferro
        for (BlockPos direction : directions) {
            if (level.getBlockState(direction).is(Blocks.IRON_BLOCK)) {
                return true; // Se algum bloco adjacente for de ferro, a estrutura é válida
            }
        }

        return false; // Se nenhum bloco adjacente for de ferro, a estrutura é inválida
    }
}