/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.rust.lang.core.psi.ext

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import org.rust.lang.core.psi.RsElementTypes.IDENTIFIER
import org.rust.lang.core.psi.RsMacroCall
import org.rust.lang.core.resolve.ref.RsMacroCallReferenceImpl
import org.rust.lang.core.resolve.ref.RsReference


abstract class RsMacroCallImplMixin(node: ASTNode) : RsCompositeElementImpl(node), RsMacroCall {

    override fun getReference(): RsReference = RsMacroCallReferenceImpl(this)

    override val referenceName: String
        get() = referenceNameElement.text

    override val referenceNameElement: PsiElement
        get() = findChildByType(IDENTIFIER)!!

}

val RsMacroCall.macroName: PsiElement? get() = referenceNameElement
