// This is a generated file. Not intended for manual editing.
package org.rust.lang.core.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface RustCastExpr extends RustExpr {

  @Nullable
  RustAbi getAbi();

  @NotNull
  List<RustAnonParam> getAnonParamList();

  @NotNull
  List<RustExpr> getExprList();

  @NotNull
  List<RustGenericArgs> getGenericArgsList();

  @Nullable
  RustGenericParams getGenericParams();

  @Nullable
  RustLifetimes getLifetimes();

  @NotNull
  List<RustRetType> getRetTypeList();

  @Nullable
  RustTraitRef getTraitRef();

  @Nullable
  RustTypeSum getTypeSum();

  @NotNull
  List<RustTypeSums> getTypeSumsList();

  @Nullable
  PsiElement getAnd();

  @Nullable
  PsiElement getAndand();

  @Nullable
  PsiElement getBox();

  @Nullable
  PsiElement getComma();

  @Nullable
  PsiElement getConst();

  @Nullable
  PsiElement getCself();

  @Nullable
  PsiElement getDotdot();

  @Nullable
  PsiElement getDotdotdot();

  @Nullable
  PsiElement getExtern();

  @Nullable
  PsiElement getFn();

  @Nullable
  PsiElement getFor();

  @Nullable
  PsiElement getGt();

  @Nullable
  PsiElement getLbrack();

  @Nullable
  PsiElement getLifetime();

  @Nullable
  PsiElement getLt();

  @Nullable
  PsiElement getMul();

  @Nullable
  PsiElement getMut();

  @Nullable
  PsiElement getRbrack();

  @Nullable
  PsiElement getSemicolon();

  @Nullable
  PsiElement getStaticLifetime();

  @Nullable
  PsiElement getTypeof();

  @Nullable
  PsiElement getUnderscore();

  @Nullable
  PsiElement getUnsafe();

}
