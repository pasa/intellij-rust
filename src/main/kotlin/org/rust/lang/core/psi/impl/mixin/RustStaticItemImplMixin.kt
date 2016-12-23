package org.rust.lang.core.psi.impl.mixin

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import org.rust.ide.icons.RustIcons
import org.rust.lang.core.psi.RustStaticItemElement
import org.rust.lang.core.psi.iconWithVisibility
import org.rust.lang.core.psi.impl.RustPsiImplUtil
import org.rust.lang.core.psi.impl.RustStubbedNamedElementImpl
import org.rust.lang.core.stubs.RustStaticItemElementStub

abstract class RustStaticItemImplMixin : RustStubbedNamedElementImpl<RustStaticItemElementStub>, RustStaticItemElement {
    constructor(node: ASTNode) : super(node)

    constructor(stub: RustStaticItemElementStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

    override fun getIcon(flags: Int) = iconWithVisibility(flags, when {
        isConstant -> RustIcons.CONSTANT
        isMut -> RustIcons.MUT_STATIC
        else -> RustIcons.STATIC
    })

    override val isPublic: Boolean get() = RustPsiImplUtil.isPublic(this, stub)
}

val RustStaticItemElement.isMut: Boolean
    get() = mut != null

val RustStaticItemElement.isConstant: Boolean get() = const != null
