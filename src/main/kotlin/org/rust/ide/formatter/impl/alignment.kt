package org.rust.ide.formatter.impl

import org.rust.ide.formatter.RsAlignmentStrategy
import org.rust.ide.formatter.blocks.RsFmtBlock
import org.rust.lang.core.psi.RsCompositeElementTypes.*

fun RsFmtBlock.getAlignmentStrategy(): RsAlignmentStrategy = when (node.elementType) {
    TUPLE_EXPR, VALUE_ARGUMENT_LIST, FORMAT_MACRO_ARGS, TRY_MACRO_ARGS, VEC_MACRO_ARGS ->
        RsAlignmentStrategy.wrap()
            .alignIf { child, parent, ctx ->
                // Do not align if we have only one argument as this may lead to
                // some quirks when that argument is tuple expr.
                // Alignment do not allow "negative indentation" i.e.:
                //     func((
                //         happy tuple param
                //     ))
                // would be formatted to:
                //     func((
                //              happy tuple param
                //          ))
                // because due to applied alignment, closing paren has to be
                // at least in the same column as the opening one.
                var result = true
                if (parent != null) {
                    val lBrace = parent.firstChildNode
                    val rBrace = parent.lastChildNode
                    if (lBrace.isBlockDelim(parent) && rBrace.isBlockDelim(parent)) {
                        result = child.treeNonWSPrev() != lBrace || child.treeNonWSNext() != rBrace
                    }
                }
                result
            }
            .alignUnlessBlockDelim()
            .alignIf(ctx.commonSettings.ALIGN_MULTILINE_PARAMETERS_IN_CALLS)

    TUPLE_TYPE, TUPLE_FIELDS ->
        RsAlignmentStrategy.wrap()
            .alignUnlessBlockDelim()
            .alignIf(ctx.commonSettings.ALIGN_MULTILINE_PARAMETERS)

    VALUE_PARAMETER_LIST ->
        RsAlignmentStrategy.shared()
            .alignUnlessBlockDelim()
            .alignIf(ctx.commonSettings.ALIGN_MULTILINE_PARAMETERS)

    in FN_DECLS ->
        RsAlignmentStrategy.shared()
            .alignIf { c, p, rustFmtContext ->
                c.elementType == RET_TYPE && ctx.rustSettings.ALIGN_RET_TYPE ||
                    c.elementType == WHERE_CLAUSE && ctx.rustSettings.ALIGN_WHERE_CLAUSE
            }

    PAT_ENUM ->
        RsAlignmentStrategy.wrap()
            .alignIf { c, p, x -> x.metLBrace && !c.isBlockDelim(p) }
            .alignIf(ctx.commonSettings.ALIGN_MULTILINE_PARAMETERS_IN_CALLS)

    METHOD_CALL_EXPR ->
        RsAlignmentStrategy.shared()
            .alignIf(DOT) // DOT is synthetic's block representative
            .alignIf(ctx.commonSettings.ALIGN_MULTILINE_CHAINED_METHODS)

    WHERE_CLAUSE ->
        RsAlignmentStrategy.wrap()
            .alignIf(WHERE_PRED)
            .alignIf(ctx.rustSettings.ALIGN_WHERE_BOUNDS)

    TYPE_PARAMETER_LIST ->
        RsAlignmentStrategy.wrap()
            .alignIf(TYPE_PARAMETER, LIFETIME_PARAMETER)
            .alignIf(ctx.rustSettings.ALIGN_TYPE_PARAMS)

    FOR_LIFETIMES ->
        RsAlignmentStrategy.wrap()
            .alignIf(LIFETIME_PARAMETER)
            .alignIf(ctx.rustSettings.ALIGN_TYPE_PARAMS)

    else -> RsAlignmentStrategy.NullStrategy
}

fun RsAlignmentStrategy.alignUnlessBlockDelim(): RsAlignmentStrategy = alignIf { c, p, x -> !c.isBlockDelim(p) }
